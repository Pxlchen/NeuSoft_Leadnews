package com.neusoft.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.model.common.dtos.PageResponseResult;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.wemedia.dtos.ChannelSaveDto;
import com.neusoft.model.wemedia.dtos.WmChannelDto;
import com.neusoft.model.wemedia.pojos.WmChannel;
import com.neusoft.model.wemedia.pojos.WmNews;
import com.neusoft.wemedia.mapper.WmChannelMapper;
import com.neusoft.wemedia.service.WmChannelService;
import com.neusoft.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    @Autowired
    private WmNewsService wmNewsService;


    /**
     * 查询所有频道
     * @return
     */
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }

    /**
     * 修改频道
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateChannel(ChannelSaveDto dto) {
        //判断参数是否为空
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断频道是否存在
        WmChannel channel = getById(dto.getChannelId());
        if (channel == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //如果频道被引用则不能禁用
        if (dto.getStatus() == null || !dto.getStatus().equals("1")){
            List<WmNews> wmNewsList = wmNewsService.list(Wrappers.<WmNews>lambdaQuery()
                    .eq(WmNews::getChannelId, dto.getChannelId()));
            if (wmNewsList.size() > 0){
                return ResponseResult.errorResult(403,"该频道被引用，不能禁用");
            }
        }
        //修改频道
        WmChannel wmChannel = new WmChannel();
        BeanUtils.copyProperties(dto,wmChannel);
        wmChannel.setId(dto.getChannelId());
        if (dto.getIsDefault() == null)
            dto.setIsDefault("0");
        wmChannel.setIsDefault(dto.getIsDefault().equals("1"));
        if (dto.getStatus() == null)
            dto.setStatus("0");
        wmChannel.setStatus(dto.getStatus().equals("1"));
        if (dto.getOrd() != null) wmChannel.setOrd(Integer.valueOf(dto.getOrd()));
        updateById(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 新增频道
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveChannel(ChannelSaveDto dto) {
        //判断参数是否为空
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断是否已经存在频道
        List<WmChannel> list = list();
        List<String> nameList = list.stream().map(WmChannel::getName).collect(Collectors.toList());
        String join = StringUtils.join(nameList, ",");
        if (join.contains(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        //复制参数，补充参数
        WmChannel wmChannel = new WmChannel();
        BeanUtils.copyProperties(dto,wmChannel);
        wmChannel.setCreatedTime(new Date());
        if (dto.getIsDefault() == null)
            dto.setIsDefault("0");
        wmChannel.setIsDefault(dto.getIsDefault().equals("1"));
        if (wmChannel.getOrd() == null){
            wmChannel.setOrd(10);
        }else {
            wmChannel.setOrd(Integer.valueOf(dto.getOrd()));
        }
        if (dto.getStatus() == null)
            dto.setStatus("0");
        wmChannel.setStatus(dto.getStatus().equals("1"));

        //新增频道
        save(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 根据名字模糊查询频道
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findList(WmChannelDto dto) {
        //1.检查参数
        //分页查询
        dto.checkParam();

        //2.分页查询条件
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmChannel> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //账号状态精确查询
        if (dto.getStatus() != null){
            lambdaQueryWrapper.eq(WmChannel::getStatus,dto.getStatus());
        }

        //关键字的模糊查询
        if (StringUtils.isNotBlank(dto.getName())){
            lambdaQueryWrapper.like(WmChannel::getName,dto.getName());
        }

        //按照发布时间倒序开始
        lambdaQueryWrapper.orderByDesc(WmChannel::getCreatedTime);

        page = page(page, lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());


        return responseResult;
    }

    /**
     * 根据ID删除频道
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteById(Integer id) {
        //判断参数是否存在
        if (id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断频道是否存在
        WmChannel wmChannel = getById(id);
        if (wmChannel == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //判断频道是否启用，启用则不能删除
        if (wmChannel.getStatus()){
            return ResponseResult.errorResult(403,"频道已启用，不能删除");
        }

        //判断是否有其他文章关联
        List<WmNews> wmNewsList = wmNewsService.list(Wrappers.<WmNews>lambdaQuery().eq(WmNews::getChannelId, wmChannel.getId()));
        if (wmNewsList.size() > 0){
            return ResponseResult.errorResult(401,"该频道与文章有关联，无法删除");
        }
        //删除频道
        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}