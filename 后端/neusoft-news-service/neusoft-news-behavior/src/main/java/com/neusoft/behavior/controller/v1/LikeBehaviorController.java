package com.neusoft.behavior.controller.v1;

import com.neusoft.behavior.service.LikeBehaviorService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.LikesBehaviorDto;
import com.neusoft.model.user.dtos.UnLikesBehaviorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LikeBehaviorController {

    @Autowired
    private LikeBehaviorService likeBehaviorService;

    /**
     * 点赞与取消点赞
     * @param dto
     * @return
     */
    @PostMapping("/likes_behavior")
    public ResponseResult likesBehavior(@RequestBody LikesBehaviorDto dto){
        return likeBehaviorService.likesBehavior(dto);
    }

    /**
     * 不喜欢与取消不喜欢
     * @param dto
     * @return
     */
    @PostMapping("/un_likes_behavior")
    public ResponseResult unLikesBehavior(@RequestBody UnLikesBehaviorDto dto){
        return likeBehaviorService.unLikesBehavior(dto);
    }

}
