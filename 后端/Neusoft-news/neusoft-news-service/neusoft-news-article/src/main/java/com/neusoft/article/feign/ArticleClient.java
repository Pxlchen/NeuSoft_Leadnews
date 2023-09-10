package com.neusoft.article.feign;

import com.neusoft.apis.article.IArticleClient;
import com.neusoft.article.service.ApArticleService;
import com.neusoft.model.article.dtos.ArticleDto;
import com.neusoft.model.article.pojos.ApArticle;
import com.neusoft.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleClient implements IArticleClient {

    @Autowired
    private ApArticleService apArticleService;

    /**
     * 保存、修改文章
     * @param dto
     * @return
     */
    @Override
    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto) {
        return apArticleService.saveArticle(dto);
    }


    /**
     * 删除文章
     * @param apArticle
     * @return
     */
    @Override
    @PostMapping("/api/v1/article/delete")
    public ResponseResult deleteArticle(ApArticle apArticle) {
        return apArticleService.deleteArticle(apArticle);
    }

}