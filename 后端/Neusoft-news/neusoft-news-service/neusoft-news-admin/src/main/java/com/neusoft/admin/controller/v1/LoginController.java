package com.neusoft.admin.controller.v1;

import com.neusoft.admin.service.AdUserService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.admin.dtos.AdLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AdUserService adUserService;

    @PostMapping("/in")
    public ResponseResult login(@RequestBody AdLoginDto dto){
        return adUserService.login(dto);
    }
}
