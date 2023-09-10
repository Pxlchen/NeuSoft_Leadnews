package com.neusoft.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.article.mapper.ApArticleConfigMapper;
import com.neusoft.article.mapper.ApArticleContentMapper;
import com.neusoft.article.mapper.ApArticleMapper;
import com.neusoft.article.service.ApArticleService;
import com.neusoft.article.service.ArticleFreemarkerService;
import com.neusoft.common.constants.ArticleConstants;
import com.neusoft.common.constants.UserBehaviorConstants;
import com.neusoft.common.exception.CustomException;
import com.neusoft.common.redis.CacheService;
import com.neusoft.file.service.FileStorageService;
import com.neusoft.model.article.dtos.ArticleDto;
import com.neusoft.model.article.dtos.ArticleHomeDto;
import com.neusoft.model.article.dtos.ArticleInfoDto;
import com.neusoft.model.article.pojos.ApArticle;
import com.neusoft.model.article.pojos.ApArticleConfig;
import com.neusoft.model.article.pojos.ApArticleContent;
import com.neusoft.model.article.vos.HotArticleVo;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.mess.ArticleVisitStreamMess;
import com.neusoft.model.user.pojos.ApUser;
import com.neusoft.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private ArticleFreemarkerService articleFreemarkerService;

    @Autowired
    private CacheService cacheService;

    private final static Short MAX_PAGE_SIZE = 50;

    /**
     * @param dto
     * @param type 1 加载更多 2加载最新
     * @return
     */
    @Override
    public ResponseResult load(ArticleHomeDto dto, Short type) {
        //1.检验参数
        //分页条数的校验
        Integer size = dto.getSize();
        if (size == null || size == 0){
            size = 10;
        }

        //分页的值不超过50
        Math.min(size, MAX_PAGE_SIZE);

        //校验参数 -->type
        if (!type.equals(ArticleConstants.LOADTYPE_LOAD_MORE) && !type.equals(ArticleConstants.LOADTYPE_LOAD_NEW)){
            type = ArticleConstants.LOADTYPE_LOAD_MORE;
        }
        
        //频道参数校验
        if (StringUtils.isBlank(dto.getTag())){
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }
        
        //时间校验
        if (dto.getMaxBehotTime() == null)dto.setMaxBehotTime(new Date());
        if (dto.getMinBehotTime() == null)dto.setMinBehotTime(new Date());
        
        //2.查询
        List<ApArticle> articleList = apArticleMapper.loadArticleList(dto, type);

        //3.返回结果
        return ResponseResult.okResult(articleList);

    }



    /**保存App端相关文章
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveArticle(ArticleDto dto) {

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //1.检查参数
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApArticle apArticle = new ApArticle();
        BeanUtils.copyProperties(dto,apArticle);

        //2.判断是否存在id
        if (dto.getId() == null){
            //2.1 不存在id 保存 文章 文章配置 文章内容

            //保存文章
            save(apArticle);

            //保存配置
            ApArticleConfig apArticleConfig = new ApArticleConfig(apArticle.getId());
            apArticleConfigMapper.insert(apArticleConfig);

            //保存 文章内容
            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.insert(apArticleContent);
        }else {
            //2.2 存在id 修改 文章 文章内容

            //修改 文章
            updateById(apArticle);

            //修改文章内容
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery()
                    .eq(ApArticleContent::getArticleId, dto.getId()));
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.updateById(apArticleContent);
        }

        //异步调用 生成静态文件上传到minio中
        articleFreemarkerService.buildArticleToMinIO(apArticle,dto.getContent());

        //3.结果返回 文章的id
        return ResponseResult.okResult(apArticle.getId());
    }

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 删除文章
     *
     * @param apArticle
     * @return
     */
    @Override
    public ResponseResult deleteArticle(ApArticle apArticle) {
        if (apArticle.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.LACK_ID);
        }
        ApArticle article = getById(apArticle.getId());
        if (article == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //删除文章配置
        apArticleConfigMapper.delete(Wrappers.<ApArticleConfig>lambdaQuery()
                .eq(ApArticleConfig::getArticleId,apArticle.getId()));
        //删除文章内容
        apArticleContentMapper.delete(Wrappers.<ApArticleContent>lambdaQuery()
                .eq(ApArticleContent::getArticleId,apArticle.getId()));
        //删除minIO中的文件
        fileStorageService.delete(article.getStaticUrl());
        //删除文章信息
        boolean result = removeById(apArticle.getId());
        if (result){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        }else {
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }

    }

    /**
     * 加载文章行为
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto) {
        //判断参数是否正确
        if (dto == null || dto.getArticleId() == null || dto.getAuthorId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取用户id
        ApUser user = AppThreadLocalUtil.getUser();
        //判断是否登录
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Map<String,Boolean> map = new HashMap<>();
        //判断是否关注
        Set<String> authorIds = cacheService.zRangeAll(UserBehaviorConstants.FOLLOW + user.getId());
        String authors = StringUtils.join(authorIds, ",");
        boolean contains = authors.contains(dto.getAuthorId().toString());
        if (contains){
            map.put("isfollow",true);
        }else {
            map.put("isfollow",false);
        }
        //判断是否点赞
        String likeJson = (String)cacheService.hGet(UserBehaviorConstants.LIKE_BEHAVIOR+dto.getArticleId(),
                user.getId().toString());
        if (likeJson != null){
            map.put("islike",true);
        }else {
            map.put("islike",false);
        }
        //判断是否不喜欢
        String unLikeJson = (String)cacheService.hGet(UserBehaviorConstants.UN_LIKE_BEHAVIOR + dto.getArticleId(),user.getId().toString());
        if (likeJson != null){
            map.put("isunlike",true);
        }else {
            map.put("isunlike",false);
        }
        //判断是否收藏
        String collectJson = (String)cacheService.hGet(UserBehaviorConstants.COLLECTION_BEHAVIOR + dto.getArticleId(),user.getId().toString());
        if (collectJson != null){
            map.put("iscollection",true);
        }else {
            map.put("iscollection",false);
        }

        return ResponseResult.okResult(map);
    }

    /**
     * 加载文字列表
     *
     * @param dto
     * @param type      1 加载更多 2加载最新
     * @param firstPage true 是首页 flase 非首页
     * @return
     */
    @Override
    public ResponseResult load2(ArticleHomeDto dto, Short type, boolean firstPage) {
        if (firstPage){//如果是首页
            String jsonStr = cacheService.get(ArticleConstants.HOT_ARTICLE_FIRST_PAGE + dto.getTag());
            if (StringUtils.isNotBlank(jsonStr)){
                List<HotArticleVo> hotArticleVoList = JSON.parseArray(jsonStr, HotArticleVo.class);
                ResponseResult responseResult = ResponseResult.okResult(hotArticleVoList);
                return responseResult;
            }
        }

        return load(dto,type);
    }

    /**
     * 更新文章的分值  同时更新缓存中的热点文章数据
     * @param mess
     */
    @Override
    public void updateScore(ArticleVisitStreamMess mess) {
        //1.更新文章的阅读、点赞、收藏、评论的数量
        ApArticle apArticle = updateArticle(mess);
        //2.计算文章的分值
        Integer score = computeScore(apArticle);
        score = score * 3;

        //3.替换当前文章对应频道的热点数据
        replaceDataToRedis(apArticle, score, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + apArticle.getChannelId());

        //4.替换推荐对应的热点数据
        replaceDataToRedis(apArticle, score, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG);

    }

    /**
     * 替换数据并且存入到redis
     * @param apArticle
     * @param score
     * @param s
     */
    private void replaceDataToRedis(ApArticle apArticle, Integer score, String s) {
        String articleListStr = cacheService.get(s);
        if (StringUtils.isNotBlank(articleListStr)) {
            List<HotArticleVo> hotArticleVoList = JSON.parseArray(articleListStr, HotArticleVo.class);

            boolean flag = true;

            //如果缓存中存在该文章，只更新分值
            for (HotArticleVo hotArticleVo : hotArticleVoList) {
                if (hotArticleVo.getId().equals(apArticle.getId())) {
                    hotArticleVo.setScore(score);
                    flag = false;
                    break;
                }
            }

            //如果缓存中不存在，查询缓存中分值最小的一条数据，进行分值的比较，如果当前文章的分值大于缓存中的数据，就替换
            if (flag) {
                if (hotArticleVoList.size() >= 30) {
                    hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
                    HotArticleVo lastHot = hotArticleVoList.get(hotArticleVoList.size() - 1);
                    if (lastHot.getScore() < score) {
                        hotArticleVoList.remove(lastHot);
                        HotArticleVo hot = new HotArticleVo();
                        BeanUtils.copyProperties(apArticle, hot);
                        hot.setScore(score);
                        hotArticleVoList.add(hot);
                    }


                } else {
                    HotArticleVo hot = new HotArticleVo();
                    BeanUtils.copyProperties(apArticle, hot);
                    hot.setScore(score);
                    hotArticleVoList.add(hot);
                }
            }
            //缓存到redis
            hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
            cacheService.set(s, JSON.toJSONString(hotArticleVoList));

        }
    }

    /**
     * 更新文章行为数量
     * @param mess
     */
    private ApArticle updateArticle(ArticleVisitStreamMess mess) {
        ApArticle apArticle = getById(mess.getArticleId());
        apArticle.setCollection(apArticle.getCollection()==null?0:apArticle.getCollection()+mess.getCollect());
        apArticle.setComment(apArticle.getComment()==null?0:apArticle.getComment()+mess.getComment());
        apArticle.setLikes(apArticle.getLikes()==null?0:apArticle.getLikes()+mess.getLike());
        apArticle.setViews(apArticle.getViews()==null?0:apArticle.getViews()+mess.getView());
        updateById(apArticle);
        return apArticle;

    }

    /**
     * 计算文章的具体分值
     * @param apArticle
     * @return
     */
    private Integer computeScore(ApArticle apArticle) {
        Integer score = 0;
        if(apArticle.getLikes() != null){
            score += apArticle.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }
        if(apArticle.getViews() != null){
            score += apArticle.getViews();
        }
        if(apArticle.getComment() != null){
            score += apArticle.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        if(apArticle.getCollection() != null){
            score += apArticle.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }

        return score;
    }
}
