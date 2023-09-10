package com.neusoft.model.user.dtos;

import com.neusoft.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class LikesBehaviorDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 0 点赞 1 取消点赞
     */
    private Short operation;

    /**
     * 0文章 1动态 2评论
     */
    private Short type;

    /**
     * 设备
     */
    @IdEncrypt
    private Long equipmentId;
}
