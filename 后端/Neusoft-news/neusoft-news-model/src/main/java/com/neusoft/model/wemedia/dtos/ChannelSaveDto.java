package com.neusoft.model.wemedia.dtos;

import lombok.Data;

@Data
public class ChannelSaveDto {

    private Integer channelId;

    /**
     * 频道名称
     */
    private String name;

    /**
     * 频道描述
     */
    private String description;

    /**
     * 是否启用
     * 1：启用   true
     * 0：禁用   false
     */
    private String status;

    /**
     * 默认排序
     */
    private String ord;

    /**
     * 是否默认频道
     * 1：默认     true
     * 0：非默认   false
     */
    private String isDefault;
}
