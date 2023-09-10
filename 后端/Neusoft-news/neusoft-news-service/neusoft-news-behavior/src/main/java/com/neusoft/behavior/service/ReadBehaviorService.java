package com.neusoft.behavior.service;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.LikesBehaviorDto;
import com.neusoft.model.user.dtos.ReadBehaviorDto;

public interface ReadBehaviorService {
    /**
     * 记录用户阅读行为
     * @param dto
     * @return
     */
    ResponseResult readBehavior(ReadBehaviorDto dto);
}
