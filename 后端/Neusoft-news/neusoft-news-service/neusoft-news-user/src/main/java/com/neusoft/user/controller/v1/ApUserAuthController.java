package com.neusoft.user.controller.v1;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.AuthDto;
import com.neusoft.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ApUserAuthController {

    @Autowired
    private ApUserRealnameService apUserRealnameService;

    /**
     * 查询列表
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody AuthDto dto){
        return apUserRealnameService.findList(dto);
    }

    /**
     * 审核失败
     * @param dto
     * @return
     */
    @PostMapping("/authFail")
    public ResponseResult authFail(@RequestBody AuthDto dto){
        return apUserRealnameService.authFail(dto);
    }

    /**
     * 审核成功
     * @param dto
     * @return
     */
    @PostMapping("/authPass")
    public ResponseResult authPass(@RequestBody AuthDto dto){
        return apUserRealnameService.authPass(dto);
    }
}
