package com.neusoft.article.service;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.CollectionBehaviorDto;
import org.springframework.web.bind.annotation.RestController;

public interface ApCollectionService {

    /**
     * 文章收藏
     * @param dto
     * @return
     */
    ResponseResult collectionBehavior(CollectionBehaviorDto dto);
}
