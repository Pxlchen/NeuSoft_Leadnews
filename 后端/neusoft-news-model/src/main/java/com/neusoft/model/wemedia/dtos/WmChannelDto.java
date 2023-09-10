package com.neusoft.model.wemedia.dtos;

import com.neusoft.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class WmChannelDto extends PageRequestDto {

    /**
     * 频道名称
     */
    private String name;

    /**
     * 是否启用
     * 1：启用   true
     * 0：禁用   false
     */
    private Boolean status;

}
