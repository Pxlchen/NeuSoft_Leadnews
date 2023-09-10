package com.neusoft.search.controller.v1;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.search.dtos.HistorySearchDto;
import com.neusoft.search.service.ApUserSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/history")
@Api(value = "app端用户搜索记录", tags = "app端用户搜索记录")
public class ApUserSearchController {

    @Autowired
    private ApUserSearchService apUserSearchService;

    @PostMapping("/load")
    @ApiOperation("查询搜索记录")
    public ResponseResult findUserSearch(){
        return apUserSearchService.findUserSearch();
    }

    @ApiOperation("删除搜索记录")
    public ResponseResult delUserSearch(@RequestBody HistorySearchDto dto){
        return apUserSearchService.delUserSearch(dto);
    }

}
