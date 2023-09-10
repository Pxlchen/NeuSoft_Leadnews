package com.neusoft.model.user.dtos;

import com.neusoft.model.common.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;

@Data
public class CollectionBehaviorDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long entryId;

    /**
     * 0 收藏 1取消收藏
     */
    private Short operation;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 0文章 1动态
     */
    private Short type;
}
