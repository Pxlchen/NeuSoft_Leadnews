package com.neusoft.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.common.constants.UserBehaviorConstants;
import com.neusoft.common.redis.CacheService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.user.dtos.UserRalationDto;
import com.neusoft.model.user.pojos.ApUser;
import com.neusoft.model.user.pojos.ApUserFollow;
import com.neusoft.user.mapper.ApUserFollowMapper;
import com.neusoft.user.service.ApUserFanService;
import com.neusoft.user.service.ApUserFollowService;
import com.neusoft.user.service.ApUserService;
import com.neusoft.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {


    @Autowired
    private ApUserService apUserService;

    @Autowired
    private ApUserFanService apUserFanService;

    @Autowired
    private CacheService cacheService;


    /**
     * 关注与取消关注
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult userFollow(UserRalationDto dto) {
        //判断参数是否为空
        if (dto == null || dto.getOperation() < 0 || dto.getOperation() > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //获取用户id
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Integer userId = user.getId();
        //获取作者id
        Integer authorId = dto.getAuthorId();

        //在redis中新增用户与作者的关注信息
        //若为关注
        if (dto.getOperation() == 0){
            //添加用户的关注信息
            cacheService.zAdd(UserBehaviorConstants.FOLLOW + userId,authorId.toString(),System.currentTimeMillis());
            //添加作者的粉丝信息
            cacheService.zAdd(UserBehaviorConstants.FAN + authorId,userId.toString(),System.currentTimeMillis());
        }
        else {//取消关注
            //移除用户关注信息
            cacheService.zRemove(UserBehaviorConstants.FOLLOW + userId,authorId.toString());
            //移除作者粉丝信息
            cacheService.zRemove(UserBehaviorConstants.FAN + authorId,userId.toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
