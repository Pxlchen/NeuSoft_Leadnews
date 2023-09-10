package com.neusoft.model.wemedia.dtos;

import lombok.Data;

@Data
public class WmNewsEnableDto {
    private Integer id;

    /**
     * 是否上下架 0 下架 1 上架
     */
    private Short enable;

}
