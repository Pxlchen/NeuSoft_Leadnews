package com.neusoft.user.controller.v1;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.UserRalationDto;
import com.neusoft.user.service.ApUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ApUserFollowController {

    @Autowired
    private ApUserFollowService apUserFollowService;

    /**
     * 关注与取消关注
     * @param dto
     * @return
     */
    @PostMapping("/user_follow")
    public ResponseResult userFollow(@RequestBody UserRalationDto dto){
        return apUserFollowService.userFollow(dto);
    }

}
