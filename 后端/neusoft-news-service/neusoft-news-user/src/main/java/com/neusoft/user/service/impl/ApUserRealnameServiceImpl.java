package com.neusoft.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.model.common.dtos.PageResponseResult;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.user.dtos.AuthDto;
import com.neusoft.model.user.pojos.ApUserRealname;
import com.neusoft.user.mapper.ApUserRealnameMapper;
import com.neusoft.user.service.ApUserRealnameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {
    /**
     * 审核失败
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult authFail(AuthDto dto) {
        //判断参数是否存在
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断数据是否存在
        ApUserRealname userRealname = getById(dto.getId());
        if (userRealname == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //修改审核状态
        userRealname.setStatus((short) 2);
        userRealname.setReason(dto.getMsg());
        updateById(userRealname);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 审核成功
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult authPass(AuthDto dto) {
        //判断参数是否存在
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断数据是否存在
        ApUserRealname userRealname = getById(dto.getId());
        if (userRealname == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //修改审核状态
        userRealname.setStatus((short) 9);
        updateById(userRealname);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 查询列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult  findList(AuthDto dto) {
        //1.检查参数
        //分页查询
        dto.checkParam();

        //2.分页查询条件
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<ApUserRealname> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //审核状态精确查询
        if (dto.getStatus() != null) {
            lambdaQueryWrapper.eq(ApUserRealname::getStatus, dto.getStatus());
        }

        page = page(page, lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());


        return responseResult;
    }
}
