package com.neusoft.search.controller.v1;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.search.dtos.UserSearchDto;
import com.neusoft.search.service.ApAssociateWordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/associate")
@Api(value = "app端用户搜索关键词联想词", tags = "app端用户搜索关键词联想词")
public class ApAssociateWordsController {

    @Autowired
    private ApAssociateWordsService apAssociateWordsService;

    /**
     * 搜索联想词
     *
     * @param dto
     * @return
     */
    @PostMapping("/search")
    @ApiOperation("搜索联想词")
    public ResponseResult search(@RequestBody UserSearchDto dto) {
        return apAssociateWordsService.search(dto);
    }
}
