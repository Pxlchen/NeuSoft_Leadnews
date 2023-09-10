package com.neusoft.search.service;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.search.dtos.UserSearchDto;

import java.io.IOException;

public interface ArticleSearchService {

    /**
     * es文章分页检索
     * @param dto
     * @return
     */
    public ResponseResult search(UserSearchDto dto) throws IOException;
}
