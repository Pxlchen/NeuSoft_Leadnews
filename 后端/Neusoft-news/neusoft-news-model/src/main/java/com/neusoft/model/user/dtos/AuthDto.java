package com.neusoft.model.user.dtos;

import com.neusoft.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class AuthDto extends PageRequestDto {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 拒绝原因
     */
    private String msg;

    /**
     * 状态
     0 创建中
     1 待审核
     2 审核失败
     9 审核通过
     */
    private Short status;
}
