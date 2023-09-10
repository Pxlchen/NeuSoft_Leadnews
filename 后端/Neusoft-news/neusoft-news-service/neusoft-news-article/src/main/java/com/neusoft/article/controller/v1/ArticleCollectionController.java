package com.neusoft.article.controller.v1;

import com.neusoft.article.service.ApCollectionService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.CollectionBehaviorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/collection_behavior")
public class ArticleCollectionController {

    @Autowired
    private ApCollectionService apCollectionService;

    /**
     * 文章收藏
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseResult collectionBehavior(@RequestBody CollectionBehaviorDto dto){
        return apCollectionService.collectionBehavior(dto);
    }
}
