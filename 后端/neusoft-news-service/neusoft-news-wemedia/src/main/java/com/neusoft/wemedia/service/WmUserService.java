package com.neusoft.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.WmLoginDto;
import com.neusoft.model.wemedia.pojos.WmUser;

public interface WmUserService extends IService<WmUser> {

    /**
     * 自媒体端登录
     * @param dto
     * @return
     */
    public ResponseResult login(WmLoginDto dto);
}