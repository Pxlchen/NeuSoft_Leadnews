package com.neusoft.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.common.exception.CustomException;
import com.neusoft.model.admin.dtos.AdSensitiveDto;
import com.neusoft.model.common.dtos.PageResponseResult;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.wemedia.dtos.SensitiveDto;
import com.neusoft.model.wemedia.pojos.WmSensitive;
import com.neusoft.utils.thread.WmThreadLocalUtil;
import com.neusoft.wemedia.mapper.WmSensitiveMapper;
import com.neusoft.wemedia.service.WmSensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitiveService {
    /**
     * 根据id删除敏感词
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteById(Integer id) {

        //1.判断id是否为空
        if (id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2.判断敏感词是否存在
        WmSensitive wmSensitive = getById(id);
        if (wmSensitive == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //3.根据id删除敏感词
        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 修改敏感词
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateSensitive(AdSensitiveDto dto) {
        //判断参数是否为空
        if (dto == null || dto.getId() == null || dto.getSensitives() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断敏感词是否存在
        WmSensitive sensitive = getById(dto.getId());
        if (sensitive == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //修改敏感词
        sensitive.setSensitives(dto.getSensitives());
        try {
            updateById(sensitive);
        } catch (Exception e) {
            throw new CustomException(AppHttpCodeEnum.SERVER_SENSITIVE_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 保存敏感词
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveSensitive(AdSensitiveDto dto) {
        //查看参数是否为空
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //复制敏感词
        WmSensitive sensitive = new WmSensitive();
        BeanUtils.copyProperties(dto,sensitive);
        sensitive.setCreatedTime(new Date());
        //保存敏感词
        save(sensitive);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    /**
     * 查看敏感词
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findList(SensitiveDto dto) {
        //1.检查参数
        //分页查询
        dto.checkParam();

        //2.分页查询条件
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmSensitive> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //关键字的模糊查询
        if (StringUtils.isNotBlank(dto.getName())){
            lambdaQueryWrapper.like(WmSensitive::getSensitives,dto.getName());
        }

        //按照发布时间倒序开始
        lambdaQueryWrapper.orderByDesc(WmSensitive::getCreatedTime);

        page = page(page, lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());


        return responseResult;

    }
}
