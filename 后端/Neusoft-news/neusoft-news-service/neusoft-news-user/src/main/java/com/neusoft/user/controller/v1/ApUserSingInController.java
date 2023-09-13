package com.neusoft.user.controller.v1;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.SignInDto;
import com.neusoft.user.service.ApUserSingInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/signin")
@Api(value = "app端用户注册",tags = "app端用户注册")
public class ApUserSingInController {

    @Autowired
    private ApUserSingInService apUserSingInService;

    /**
     * 用户注册
     * @param dto
     * @return
     */
    @PostMapping("/signin")
    @ApiOperation("用户注册")
    public ResponseResult ApUserSignIn(@RequestBody SignInDto dto){
        return apUserSingInService.ApUserSignIn(dto);
    }

}
