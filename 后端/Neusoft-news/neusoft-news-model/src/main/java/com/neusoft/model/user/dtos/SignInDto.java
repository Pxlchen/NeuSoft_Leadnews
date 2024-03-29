package com.neusoft.model.user.dtos;

import lombok.Data;

@Data
public class SignInDto {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String name;

    /**
     * 学号或者教师号
     */
    private String number;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 密码,md5加密
     */
    private String password;

    /**
     * 注册类型 0 身份证号 1 学号或工号
     */
    private Short type;


}
