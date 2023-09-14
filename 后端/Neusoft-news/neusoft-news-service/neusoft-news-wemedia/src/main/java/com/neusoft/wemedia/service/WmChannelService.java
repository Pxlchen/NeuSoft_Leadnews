package com.neusoft.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.ChannelSaveDto;
import com.neusoft.model.wemedia.dtos.WmChannelDto;
import com.neusoft.model.wemedia.pojos.WmChannel;

public interface WmChannelService extends IService<WmChannel> {


    /**
     * 查询所有频道
     * @return
     */
    ResponseResult findAll();

    /**
     * 根据ID删除频道
     * @param id
     * @return
     */
    ResponseResult deleteById(Integer id);


    /**
     * 根据名字模糊查询频道
     * @param dto
     * @return
     */
    ResponseResult findList(WmChannelDto dto);

    /**
     * 新增频道
     * @param dto
     * @return
     */
    ResponseResult saveChannel(ChannelSaveDto dto);

    /**
     * 修改频道
     * @param dto
     * @return
     */
    ResponseResult updateChannel(ChannelSaveDto dto);
}