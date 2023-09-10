package com.neusoft.model.wemedia.dtos;

import lombok.Data;

@Data
public class ChannelSaveDto {

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
    private Boolean status;

    /**
     * 默认排序
     */
    private Integer ord;
}
