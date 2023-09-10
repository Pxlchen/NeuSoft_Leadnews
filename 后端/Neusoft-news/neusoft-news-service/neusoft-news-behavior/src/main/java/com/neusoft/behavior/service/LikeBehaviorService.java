package com.neusoft.behavior.service;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.LikesBehaviorDto;
import com.neusoft.model.user.dtos.UnLikesBehaviorDto;

public interface LikeBehaviorService {
    /**
     * 点赞与取消点赞
     * @param dto
     * @return
     */
    ResponseResult likesBehavior(LikesBehaviorDto dto);

    /**
     * 不喜欢与取消不喜欢
     * @param dto
     * @return
     */
    ResponseResult unLikesBehavior(UnLikesBehaviorDto dto);
}
