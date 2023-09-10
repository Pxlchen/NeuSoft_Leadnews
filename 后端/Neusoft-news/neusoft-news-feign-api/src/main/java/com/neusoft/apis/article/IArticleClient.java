package com.neusoft.apis.article;

import com.neusoft.model.article.dtos.ArticleDto;
import com.neusoft.model.article.pojos.ApArticle;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.apis.fallback.IArticleClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "leadnews-article", fallback = IArticleClientFallback.class)
public interface IArticleClient {

    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto) ;


    @PostMapping("/api/v1/article/delete")
    public ResponseResult deleteArticle(@RequestBody ApArticle apArticle);


}