package com.neusoft.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.UserRalationDto;
import com.neusoft.model.user.pojos.ApUserFollow;

public interface ApUserFollowService extends IService<ApUserFollow> {
    /**
     * 关注与取消关注
     * @param dto
     * @return
     */
    ResponseResult userFollow(UserRalationDto dto);
}
