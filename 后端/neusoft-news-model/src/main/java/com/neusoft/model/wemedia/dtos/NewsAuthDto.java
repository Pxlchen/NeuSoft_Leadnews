package com.neusoft.model.wemedia.dtos;

import com.neusoft.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class NewsAuthDto extends PageRequestDto {
    /**
     * 主键
     */
    private Integer id;

    private String msg;

    /**
     * 标题
     */
    private String title;

    /**
     * 当前状态
     0 草稿
     1 提交（待审核）
     2 审核失败
     3 人工审核
     4 人工审核通过
     8 审核通过（待发布）
     9 已发布
     */
    private Integer status;

    /**
     * 页面开始条数
     */
    private Integer startPage;
}
