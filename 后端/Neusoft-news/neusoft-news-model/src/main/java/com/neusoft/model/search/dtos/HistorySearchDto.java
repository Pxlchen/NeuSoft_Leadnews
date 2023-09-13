package com.neusoft.model.search.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HistorySearchDto {
    /**
     * 接收搜索历史记录id
     */
    @ApiModelProperty(value = "搜索历史记录id", required = true)
    private String searchId;

    /**
     * 设备id
     */
    private String equipmentId;
}