package com.neusoft.model.admin.dtos;


import lombok.Data;

@Data
public class AdLoginDto {

    /**
     * 管理员用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
}
