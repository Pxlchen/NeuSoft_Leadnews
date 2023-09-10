package com.neusoft.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.LoginDto;
import com.neusoft.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser> {

    /**
     * app端登录功能
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
}
