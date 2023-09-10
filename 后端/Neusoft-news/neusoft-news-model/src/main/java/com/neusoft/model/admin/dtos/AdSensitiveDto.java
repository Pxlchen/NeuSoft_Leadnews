package com.neusoft.model.admin.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class AdSensitiveDto {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 敏感词
     */
    private String sensitives;

}
