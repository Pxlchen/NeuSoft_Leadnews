package com.neusoft.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.article.dtos.ArticleDto;
import com.neusoft.model.article.dtos.ArticleHomeDto;
import com.neusoft.model.article.dtos.ArticleInfoDto;
import com.neusoft.model.article.pojos.ApArticle;
import com.neusoft.model.article.pojos.ApArticleConfig;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.mess.ArticleVisitStreamMess;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApArticleService extends IService<ApArticle> {

    /**
     * 加载文字列表
     * @param dto
     * @param type 1 加载更多 2加载最新
     * @return
     */
    public ResponseResult load(ArticleHomeDto dto, Short type);

    /**
     * 加载文字列表
     * @param dto
     * @param type 1 加载更多 2加载最新
     * @param firstPage true 是首页 flase 非首页
     * @return
     */
    public ResponseResult load2(ArticleHomeDto dto, Short type, boolean firstPage);

    /**
     * 保存App端相关文章
     * @param dto
     * @return
     */
    public ResponseResult saveArticle(@RequestBody ArticleDto dto);


    /**
     * 删除文章
     * @param apArticle
     * @return
     */
    ResponseResult deleteArticle(ApArticle apArticle);

    /**
     * 加载文章行为
     * @param dto
     * @return
     */
    ResponseResult loadArticleBehavior(ArticleInfoDto dto);

    /**
     * 更新文章的分值  同时更新缓存中的热点文章数据
     * @param mess
     */
    public void updateScore(ArticleVisitStreamMess mess);
}
