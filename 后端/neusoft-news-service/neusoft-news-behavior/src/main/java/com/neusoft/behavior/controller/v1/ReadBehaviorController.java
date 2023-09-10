package com.neusoft.behavior.controller.v1;

import com.neusoft.behavior.service.ReadBehaviorService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.ReadBehaviorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ReadBehaviorController {

    @Autowired
    private ReadBehaviorService readBehaviorService;

    /**
     * 记录用户阅读行为
     * @param dto
     * @return
     */
    @PostMapping("/read_behavior")
    public ResponseResult ReadBehavior(@RequestBody ReadBehaviorDto dto){
        return readBehaviorService.readBehavior(dto);
    }
}
