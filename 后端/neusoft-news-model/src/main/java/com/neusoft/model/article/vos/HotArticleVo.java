package com.neusoft.model.article.vos;

import com.neusoft.model.article.pojos.ApArticle;
import lombok.Data;

@Data
public class HotArticleVo extends ApArticle {

    /**
     * 文章分值
     */
    private Integer score;
}
