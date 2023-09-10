package com.neusoft.wemedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.neusoft.apis.article.IArticleClient;
import com.neusoft.common.baiduyun.AipContentCensorClientConfig;
import com.neusoft.common.tess4j.Tess4jClient;
import com.neusoft.file.service.FileStorageService;
import com.neusoft.model.article.dtos.ArticleDto;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.pojos.WmChannel;
import com.neusoft.model.wemedia.pojos.WmNews;
import com.neusoft.model.wemedia.pojos.WmSensitive;
import com.neusoft.model.wemedia.pojos.WmUser;
import com.neusoft.utils.common.SensitiveWordUtil;
import com.neusoft.wemedia.mapper.WmChannelMapper;
import com.neusoft.wemedia.mapper.WmNewsMapper;
import com.neusoft.wemedia.mapper.WmSensitiveMapper;
import com.neusoft.wemedia.mapper.WmUserMapper;
import com.neusoft.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    @Autowired
    private IArticleClient articleClient;

    @Autowired
    private WmChannelMapper wmChannelMapper;

    @Autowired
    private WmUserMapper wmUserMapper;

    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private Tess4jClient tess4jClient;

    /**
     * 自媒体文章审核
     *
     * @param id 自媒体文章id
     */
    @Override
    @Async //标明当前方法是一个异步方法
    public void autoScanWmNews(Integer id) {

        //1.查询自媒体文章
        WmNews wmNews = wmNewsMapper.selectById(id);
        if (wmNews == null){
            throw new RuntimeException("WmNewAutoScanServiceImpl-文章不存在");
        }

        if (wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode())){
            //从内容中提取纯文本内容和图片
            Map<String, Object> textAndImages = handleTextAndImages(wmNews);

            //自管理的敏感词过滤
            boolean isSensitive = handleSensitiveScan((String) textAndImages.get("content"), wmNews);
            if (!isSensitive)return;

            //2.审核文本内容， 百度云接口
            boolean isTextScan = handleTextScan((String) textAndImages.get("content"),wmNews);
            if (!isTextScan)return;

            //3.审核图片 百度云接口
            boolean isImageScan = handleImageScan((List<String>) textAndImages.get("images"), wmNews);
            if (!isImageScan)return;

            //4.审核成功，保存app端的相关的文章数据
            ResponseResult responseResult = saveAppArticle(wmNews);
            if (!responseResult.getCode().equals(200)){
//                updateWmNews(wmNews,(short) 2,"保存数据出现异常");
                throw new RuntimeException("WmNewAutoScanServiceImpl-文章审核，保存app端相关文章数据失败");
            }
            //回填article_id
            wmNews.setArticleId((Long) responseResult.getData());
            updateWmNews(wmNews,(short) 9,"审核成功");
        }

    }



    /**
     * 自管理的敏感词审核
     * @param content
     * @param wmNews
     * @return
     */
    private boolean handleSensitiveScan(String content, WmNews wmNews) {

        boolean flag = true;

        //获取所有的敏感词
        List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(Wrappers.<WmSensitive>lambdaQuery().select(WmSensitive::getSensitives));
        List<String> sensitiveList = wmSensitives.stream().map(WmSensitive::getSensitives).collect(Collectors.toList());

        //初始化敏感词库
        SensitiveWordUtil.initMap(sensitiveList);

        //查看文章中是否包含敏感词
        Map<String, Integer> map = SensitiveWordUtil.matchWords(content);
        if (map.size() > 0){
            updateWmNews(wmNews,(short) 2,"当前文章中存在违规内容"+map);
            flag = false;
        }

        return flag;

    }



    /**
     * 保存app端相关的文章数据
     * @param wmNews
     * @return
     */
//    @GlobalTransactional
    public ResponseResult saveAppArticle(WmNews wmNews) {
        ArticleDto dto = new ArticleDto();
        //属性拷贝
        BeanUtils.copyProperties(wmNews,dto);
        //文章的布局
        dto.setLayout(wmNews.getType());
        //频道
        WmChannel wmChannel = wmChannelMapper.selectById(wmNews.getChannelId());
        if (wmChannel != null){
            dto.setChannelName(wmChannel.getName());
        }
        //作者
        dto.setAuthorId(wmNews.getUserId().longValue());
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        if (wmUser != null){
            dto.setAuthorName(wmUser.getName());
        }

        //设置文章id
        if (wmNews.getArticleId() != null){
            dto.setId(wmNews.getArticleId());
        }
        dto.setCreatedTime(new Date());

        ResponseResult responseResult = articleClient.saveArticle(dto);
        return responseResult;
    }



    /**
     * 审核图片
     * @param images
     * @param wmNews
     * @return
     */
    private boolean handleImageScan(List<String> images, WmNews wmNews) {

        boolean flag = true;

        if (images == null || images.size() == 0){
            return flag;
        }

        //下载图片 minIo
        //图片去重
        images = images.stream().distinct().collect(Collectors.toList());

        List<byte[]> imageList = new ArrayList<>();

        try {
            for (String image : images) {
                byte[] bytes = fileStorageService.downLoadFile(image);

                //byte[] 转换为bufferedImage
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                BufferedImage bufferedImage = ImageIO.read(in);

                //图片识别
                String result = tess4jClient.doOCR(bufferedImage);
                //过滤文字
                boolean isSensitive = handleSensitiveScan(result, wmNews);
                if (!isSensitive){
                    return isSensitive;
                }

                imageList.add(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //审核图片
        try{
            Map map = aipContentCensorClientConfig.getImageCensorResult(imageList);
            if (map != null){
                //审核失败
                if (map.get("conclusionType").equals("2")){
                    flag = false;
                    updateWmNews(wmNews,(short) 2,"当前文章中存在违规内容");
                }

                //不确定信息 需要人工审核
                if (map.get("conclusionType").equals("3")){
                    flag = false;
                    updateWmNews(wmNews,(short) 3,"当前文章中存在不确定内容");
                }
            }
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }

        return flag;

    }

    @Autowired
    private AipContentCensorClientConfig aipContentCensorClientConfig;

    /**
     * 审核文本内容
     * @param content
     * @param wmNews
     * @return
     */
    private boolean handleTextScan(String content, WmNews wmNews) {

        boolean flag = true;

        if ((wmNews.getTitle()+"-"+content).length() == 0){
            return flag;
        }

        try{
            Map map = aipContentCensorClientConfig.getCommonTextCensorResult((wmNews.getTitle()+"-"+content));
            if (map != null){
                //审核失败
                if (map.get("conclusionType").equals("2")){
                    flag = false;
                    updateWmNews(wmNews,(short) 2,"当前文章中存在违规内容");
                }

                //不确定信息 需要人工审核
                if (map.get("conclusionType").equals("3")){
                    flag = false;
                    updateWmNews(wmNews,(short) 3,"当前文章中存在不确定内容");
                }
            }
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }

        return flag;

    }

    /**
     * 修改文章内容
     * @param wmNews
     * @param status
     * @param reason
     */
    private void updateWmNews(WmNews wmNews, short status, String reason){
        wmNews.setStatus(status);
        wmNews.setReason(reason);
        wmNewsMapper.updateById(wmNews);
    }

    public Map<String, Object> handleTextAndImages(WmNews wmNews){

        //存储纯文本内容
        StringBuilder stringBuilder = new StringBuilder();

        //图片内容集合
        List<String> images = new ArrayList<>();

        //1.从自媒体文章的内容中提取文本和图片
        if (StringUtils.isNotBlank(wmNews.getContent())){
            List<Map> maps = JSONArray.parseArray(wmNews.getContent(), Map.class);
            for (Map map : maps) {
                if (map.get("type").equals("text")){
                    stringBuilder.append(map.get("value"));
                }

                if (map.get("type").equals("image")){
                    images.add((String) map.get("value"));
                }
            }
        }
        //2.提取文章的封面图片
        if (StringUtils.isNotBlank(wmNews.getImages())){
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("content",stringBuilder.toString());
        resultMap.put("images",images);
        return resultMap;
    }
}
