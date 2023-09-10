package com.neusoft.search.controller.v1;


import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.search.dtos.UserSearchDto;
import com.neusoft.search.service.ArticleSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/article/search")
@Api(value = "app端文章分页检索", tags = "app端文章分页检索")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * es文章分页检索
     *
     * @param dto
     * @return
     */
    @PostMapping("/search")
    @ApiOperation("文章分页检索")
    public ResponseResult search(@RequestBody UserSearchDto dto) throws IOException {
        return articleSearchService.search(dto);
    }
}
