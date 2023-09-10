package com.neusoft.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.admin.dtos.AdLoginDto;
import com.neusoft.model.admin.pojos.AdUser;
import com.neusoft.model.common.dtos.ResponseResult;

public interface AdUserService extends IService<AdUser> {

    /**
     * 管理员登录
     * @param dto
     * @return
     */
    ResponseResult login(AdLoginDto dto);
}
