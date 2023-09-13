package com.neusoft.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.user.dtos.SignInDto;
import com.neusoft.model.user.pojos.ApUser;
import com.neusoft.user.service.ApUserService;
import com.neusoft.user.service.ApUserSingInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ApUserSingInServiceImpl implements ApUserSingInService {

    @Autowired
    private ApUserService apUserService;

    /**
     * 用户注册
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult ApUserSignIn(SignInDto dto) {
        //判断参数是否有效
        if (dto == null || dto.getPhone() == null || dto.getType() < 0 || dto.getType() > 1 || dto.getName() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //判断手机号是否已经注册
        ApUser dbUser = apUserService.getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
        if (dbUser != null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        //判断注册类型 学号或者身份证
        if (dto.getType() == 0){
            //身份证注册
            if (dto.getIdNumber() == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            //身份证是否已注册
            dbUser = apUserService.getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getIdNumber,dto.getIdNumber()));
            if (dbUser != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
            }
            //身份证验证



        }else {
            //学号注册
            if (dto.getNumber() == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            //学号是否已经注册
            dbUser = apUserService.getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getNumber,dto.getNumber()));
            if (dbUser != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
            }
            //学号验证,无法拿到学号数据，暂时定为判断是否为11位
            int length = String.valueOf(dto.getNumber()).length();
            if (length != 11){
                return ResponseResult.errorResult(403,"学号验证失败");
            }
        }

        //进行注册

        return null;
    }
}
