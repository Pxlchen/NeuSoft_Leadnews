package com.neusoft.model.user.dtos;

import com.neusoft.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class UnLikesBehaviorDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 0 不喜欢 1取消不喜欢
     */
    private Short type;
}
