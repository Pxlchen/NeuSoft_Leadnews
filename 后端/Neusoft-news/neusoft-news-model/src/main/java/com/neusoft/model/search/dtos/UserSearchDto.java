package com.neusoft.model.search.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class UserSearchDto {

    /**
    * 搜索关键字
    */
    @ApiModelProperty(value = "搜索关键字", required = true)
    String searchWords;
    /**
    * 当前页
    */
    @ApiModelProperty(value = "当前页", required = true)
    int pageNum;
    /**
    * 分页条数
    */
    @ApiModelProperty(value = "分页条数", required = true)
    int pageSize;
    /**
    * 最小时间
    */
    @ApiModelProperty(value = "最小时间")
    Date minBehotTime;

    public int getFromIndex(){
        if(this.pageNum<1)return 0;
        if(this.pageSize<1) this.pageSize = 10;
        return this.pageSize * (pageNum-1);
    }
}