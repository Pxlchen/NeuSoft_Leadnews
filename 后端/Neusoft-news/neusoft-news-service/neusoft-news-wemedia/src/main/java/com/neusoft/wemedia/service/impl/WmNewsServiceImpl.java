package com.neusoft.wemedia.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.apis.article.IArticleClient;
import com.neusoft.common.constants.WemediaConstants;
import com.neusoft.common.constants.WmNewsMessageConstants;
import com.neusoft.common.exception.CustomException;
import com.neusoft.model.article.pojos.ApArticle;
import com.neusoft.model.common.dtos.PageResponseResult;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.wemedia.dtos.NewsAuthDto;
import com.neusoft.model.wemedia.dtos.WmNewsDto;
import com.neusoft.model.wemedia.dtos.WmNewsEnableDto;
import com.neusoft.model.wemedia.dtos.WmNewsPageReqDto;
import com.neusoft.model.wemedia.pojos.WmMaterial;
import com.neusoft.model.wemedia.pojos.WmNews;
import com.neusoft.model.wemedia.pojos.WmNewsMaterial;
import com.neusoft.model.wemedia.pojos.WmUser;
import com.neusoft.model.wemedia.vo.WmNewsVo;
import com.neusoft.utils.thread.WmThreadLocalUtil;
import com.neusoft.wemedia.mapper.WmMaterialMapper;
import com.neusoft.wemedia.mapper.WmNewsMapper;
import com.neusoft.wemedia.mapper.WmNewsMaterialMapper;
import com.neusoft.wemedia.service.WmNewsService;
import com.neusoft.wemedia.service.WmNewsTaskService;
import com.neusoft.wemedia.service.WmUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {


    @Autowired
    private WmNewsTaskService wmNewsTaskService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    @Autowired
    private WmNewsMapper wmUserMapper;

    @Autowired
    private WmUserService wmUserService;

    @Autowired
    private WmNewsAutoScanServiceImpl wmNewsAutoScanServiceImpl;

    /**
     * 文章审核成功（人工）
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult authPass(NewsAuthDto dto) {
        //判断参数是否存在
        if(dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询文章，判断文章是否存在
        WmNews wmNews = getById(dto.getId());
        if (wmNews == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //文章存在创建app端的文章信息，并更新自媒体文章的状态
        ResponseResult responseResult = wmNewsAutoScanServiceImpl.saveAppArticle(wmNews);
        if (!responseResult.getCode().equals(200)){
            throw new RuntimeException("WmNewServiceImpl-文章审核，保存app端相关文章数据失败");
        }
        //回填article_id，修改文章状态
        wmNews.setArticleId((Long) responseResult.getData());
        wmNews.setStatus((short) 9);
        wmNews.setReason("审核成功");
        updateById(wmNews);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 文章审核失败
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult authFail(NewsAuthDto dto) {
        //判断参数是否存在
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询文章，判断文章是否存在
        WmNews wmNews = getById(dto.getId());
        if (wmNews == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //拒绝原因，修改状态
        wmNews.setReason(dto.getMsg());
        wmNews.setStatus((short) 2);
        updateById(wmNews);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 查询文章详情
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult queryOne(Integer id) {
        //判断参数是否为空
        if (id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询文章，判断文章是否存在
        WmNews wmNews = getById(id);
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //查询文章对应的作者名
        WmUser wmUser = wmUserService.getOne(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getId, wmNews.getUserId()));
        //封装返回结果
        WmNewsVo wmNewsVo = new WmNewsVo();
        BeanUtils.copyProperties(wmNews,wmNewsVo);
        wmNewsVo.setAuthorName(wmUser.getName());
        return ResponseResult.okResult(wmNewsVo);
    }

    /**
     * 查询文章列表
     * （管理员）
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult AdQueryList(NewsAuthDto dto) {
        //判断参数是否为空
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //构建查询条件，查询条数
        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据标题模糊查询
        if (StringUtils.isNotBlank(dto.getTitle()))
            lambdaQueryWrapper.like(WmNews::getTitle, dto.getTitle());
        //根据状态精确查询
        if (dto.getStatus() != null)
            lambdaQueryWrapper.eq(WmNews::getStatus, dto.getStatus());
        int total = count(lambdaQueryWrapper);

        //分页查询
        dto.checkParam();//检查页码和大小
        dto.setStartPage(dto.getSize()*(dto.getPage()-1));//页面查询条数
        List<WmNewsVo> wmNewsVos = wmUserMapper.AdQueryList(dto);

        //封装结果
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), total);
        pageResponseResult.setData(wmNewsVos);

        //返回结果
        return pageResponseResult;
    }

    /**
     * 条件查询文章列表（自媒体用户）
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findList(WmNewsPageReqDto dto) {
        //1.检查参数
        //分页查询
        dto.checkParam();

        //2.分页查询条件
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //状态精确查询
        if (dto.getStatus() != null) {
            lambdaQueryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }

        //频道精确查询
        if (dto.getChannelId() != null) {
            lambdaQueryWrapper.eq(WmNews::getChannelId, dto.getChannelId());
        }

        //时间范围查询
        if (dto.getBeginPubDate() != null && dto.getEndPubDate() != null) {
            lambdaQueryWrapper.between(WmNews::getPublishTime, dto.getBeginPubDate(), dto.getEndPubDate());
        }

        //关键字的模糊查询
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            lambdaQueryWrapper.like(WmNews::getTitle, dto.getKeyword());
        }
        //查询当前登录人的文章
        lambdaQueryWrapper.eq(WmNews::getUserId, WmThreadLocalUtil.getUser().getId());

        //按照发布时间倒序开始
        lambdaQueryWrapper.orderByDesc(WmNews::getPublishTime);

        page = page(page, lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());


        return responseResult;
    }


    /**
     * 发布、修改文章或保存为草稿
     *
     * @param dto
     * @return
     */
    @Override
//    @GlobalTransactional
    public ResponseResult submitNews(WmNewsDto dto) {

        //0.条件判断
        if (dto == null || dto.getContent() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //1.保存或修改文章
        WmNews wmNews = new WmNews();
        //属性拷贝，属性名词和类型相同才能拷贝
        BeanUtils.copyProperties(dto, wmNews);
        //封面图片 list ----> string
        if (dto.getImages() != null && dto.getImages().size() > 0) {
            //[djiaf.jpg,sfaoi.jpg]--> djiaf.jpg,sfaoi.jpg
            String imageStr = StringUtils.join(dto.getImages(), ",");
            wmNews.setImages(imageStr);
        }
        //如果当前封面类型为自动 -1
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)) {
            wmNews.setType(null);
        }

        //保存文章，如果为修改则删除文章与图片的关系在更新文章
        saveOrUpdateWmNews(wmNews);

        //2.判断是否为草稿，如果为草稿结束当前方法
        if (dto.getStatus().equals(WmNews.Status.NORMAL.getCode())) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        //3.不是草稿，保存文章内容图片与素材的关系
        //获取到文章内容中的图片信息
        List<String> materials = ectractUrlInfo(dto.getContent());
        saveRelativeInfoForContent(materials, wmNews.getId());

        //4.不是草稿，保存文章封面图片与素材的关系，如果当前布局是自动，需要匹配封面图片
        saveRelativeInfoForCover(dto, wmNews, materials);

        //审核文章
//        wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
        /*wmNewsTaskService.addNewsToTask(wmNews.getId(), wmNews.getPublishTime());*/


        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 查看文章详情
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult queryById(Integer id) {
        if (id == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        WmNews wmNews = getById(id);
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        return ResponseResult.okResult(wmNews);
    }

    /**
     * 文章删除
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteNewsById(Integer id) {
        //判断id是否为空
        if (id == null) return ResponseResult.errorResult(AppHttpCodeEnum.LACK_ID);
        WmNews wmNews = getOne(Wrappers.<WmNews>lambdaQuery().eq(WmNews::getId, id));
        //查询数据是否存在
        if (wmNews == null) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        //判断文章是否为已发布
        if (wmNews.getStatus() == 9) {
            //判断文章是否为下架文章，下架文章需要远程调用删除
            if (wmNews.getEnable() == 0) {
                ApArticle apArticle = new ApArticle();
                apArticle.setId(wmNews.getArticleId());
                ResponseResult responseResult = articleClient.deleteArticle(apArticle);
                if (responseResult.getCode() != 200) throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.ARTICLE_PUBLISHED);
            }
        }
        //删除文章与素材的关联关系
        wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId, id));
        //删除文章
        boolean result = removeById(id);
        if (result) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        } else {
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Autowired
    private IArticleClient articleClient;

    /**
     * 文章上下架，openfeign远程调用
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult downOrUp(WmNewsEnableDto dto) {
        //判断id是否为空
        if (dto.getId() == null) return ResponseResult.errorResult(AppHttpCodeEnum.LACK_ID);
        WmNews wmNews = getById(dto.getId());
        //判断文章是否存在
        if (wmNews == null) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        //判断文章是否为发布状态
        if (!wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode()))
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "当前文章不是发布状态，不能上下架");
        //设置上下架,修改文章enable
        boolean result = false;
        if (dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2) {
            wmNews.setEnable(dto.getEnable());
            result = updateById(wmNews);

            if (wmNews.getArticleId() != null) {
                //发送消息，通知article修改文章的配置
                Map<String, Object> map = new HashMap<>();
                map.put("articleId", wmNews.getArticleId());
                map.put("enable", dto.getEnable());
                kafkaTemplate.send(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC, JSON.toJSONString(map));
            }

        }
        if (result) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        } else {
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }

    }

    /**
     * 处理文章内容图片与素材的关系
     *
     * @param materials
     * @param id
     */
    private void saveRelativeInfoForContent(List<String> materials, Integer id) {
        saveRelativeInfo(materials, id, WemediaConstants.WM_CONTENT_REFERENCE);
    }

    /**
     * 第一个功能：如果当前封面类型为自动，则设置封面类型的数据
     * 匹配规则：
     * 1.如果内容图片大于等于1，小于3，单图 type 1
     * 2.如果内容图片大于等于3，多图 type 3
     * 3.如果内容没有图片，无图，type 0
     * <p>
     * 第二个功能：保存封面图片与素材的关系
     */
    private void saveRelativeInfoForCover(WmNewsDto dto, WmNews wmNews, List<String> materials) {
        List<String> images = dto.getImages();

        //如果当前封面类型为自动，则设置封面类型的数据
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)) {
            //多图
            if (materials.size() >= 3) {
                wmNews.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
                images = materials.stream().limit(3).collect(Collectors.toList());
            } else if (materials.size() >= 1 && materials.size() < 3) {
                //单图
                wmNews.setType(WemediaConstants.WM_NEWS_SINGLE_IMAGE);
                images = materials.stream().limit(1).collect(Collectors.toList());
            } else {
                //无图
                wmNews.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
            }
            //修改文章
            if (images != null && images.size() > 0) {
                wmNews.setImages(StringUtils.join(images, ","));
            }
            updateById(wmNews);
        }
        if (images != null && images.size() > 0) {
            saveRelativeInfo(images, wmNews.getId(), WemediaConstants.WM_COVER_REFERENCE);
        }
    }


    /**
     * 保存文章图片与素材的关系到数据库中
     *
     * @param materials
     * @param newsId
     * @param type
     */
    private void saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
        if (materials != null && !materials.isEmpty()) {
            //通过图片的url查询素材的id
            List<WmMaterial> dbMaterials = wmMaterialMapper.
                    selectList(Wrappers.<WmMaterial>lambdaQuery().in(WmMaterial::getUrl, materials));

            //判断素材是否有效
            if (dbMaterials == null || dbMaterials.size() == 0) {
                //手动抛出异常， 第一个功能：能够提示调用者素材失效了，第二个功能：进行数据的回滚
                throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }
            List<String> distinctmaterials = materials.stream().distinct().collect(Collectors.toList());

            if (distinctmaterials.size() != dbMaterials.size()) {
                throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }

            List<Integer> idList = dbMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());

            //批量保存
            wmNewsMaterialMapper.saveRelations(idList, newsId, type);
        }
    }


    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    /**
     * 保存或修改文章
     *
     * @param wmNews
     */
    private void saveOrUpdateWmNews(WmNews wmNews) {
        //补全属性
        wmNews.setUserId(WmThreadLocalUtil.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setEnable((short) 1);//默认上架

        if (wmNews.getId() == null) {
            //保存
            save(wmNews);
        } else {
            //修改
            //删除文章图片与素材的关系
            wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery()
                    .eq(WmNewsMaterial::getNewsId, wmNews.getId()));
            updateById(wmNews);
        }
    }

    /**
     * 提取文章内容中的图片信息
     *
     * @param content
     * @return
     */
    private List<String> ectractUrlInfo(String content) {
        List<String> materials = new ArrayList<>();

        List<Map> maps = JSON.parseArray(content, Map.class);
        for (Map map : maps) {
            if (map.get("type").equals("image")) {
                String imgUrl = (String) map.get("value");
                materials.add(imgUrl);
            }
        }

        return materials;
    }
}
