package com.neusoft.model.user.dtos;

import com.neusoft.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class UserRalationDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 作者Id
     */
    private Integer authorId;

    /**
     * 方式 0 关注 1 取消
     */
    private Short operation;
}
