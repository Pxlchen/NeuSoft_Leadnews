package com.neusoft.user.service;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.SignInDto;

public interface ApUserSingInService {
    /**
     * 用户注册
     * @param dto
     * @return
     */
    ResponseResult ApUserSignIn(SignInDto dto);
}
