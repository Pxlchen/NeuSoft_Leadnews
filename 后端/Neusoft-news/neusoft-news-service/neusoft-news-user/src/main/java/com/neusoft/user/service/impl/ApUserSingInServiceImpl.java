package com.neusoft.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.user.dtos.SignInDto;
import com.neusoft.model.user.pojos.ApUser;
import com.neusoft.user.service.ApUserService;
import com.neusoft.user.service.ApUserSingInService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;

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
        if (dto == null || dto.getPassword() == null || dto.getPhone() == null || dto.getType() < 0 || dto.getType() > 1 || dto.getName() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //判断手机号是否已经注册
        ApUser dbUser = apUserService.getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
        if (dbUser != null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        //判断注册类型 学号或者身份证
        if (dto.getType() == 0){
            //身份证验证注册注册
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
            //学号验证注册
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
            if (length != 11) {
                return ResponseResult.errorResult(403, "学号验证失败");
            }
        }

        //进行注册
        //生成1-6位的盐
        String salt = RandomStringUtils.randomNumeric(6);
        log.info("盐的为：{}", salt);
        //将前端传过来的密码与盐进行加密
        String dbPassword = DigestUtils.md5DigestAsHex((dto.getPassword() + salt).getBytes());
        dto.setPassword(dbPassword);
        //封装用户类，新增字段
        ApUser apUser = new ApUser();
        BeanUtils.copyProperties(dto, apUser);
        apUser.setSalt(salt);
        apUser.setCreatedTime(new Date());
        apUserService.save(apUser);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
