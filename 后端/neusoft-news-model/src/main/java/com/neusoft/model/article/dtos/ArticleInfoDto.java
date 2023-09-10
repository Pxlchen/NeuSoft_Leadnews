package com.neusoft.model.article.dtos;

import com.neusoft.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class ArticleInfoDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 作者id
     */
    private Integer authorId;
}
