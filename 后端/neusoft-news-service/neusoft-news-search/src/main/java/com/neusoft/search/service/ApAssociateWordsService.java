package com.neusoft.search.service;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.search.dtos.UserSearchDto;

public interface ApAssociateWordsService {

    /**
     * 搜索联想词
     * @param dto
     * @return
     */
    public ResponseResult search(UserSearchDto dto);
}
