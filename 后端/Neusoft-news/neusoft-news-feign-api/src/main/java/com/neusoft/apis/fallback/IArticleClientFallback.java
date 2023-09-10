package com.neusoft.apis.fallback;

import com.neusoft.apis.article.IArticleClient;
import com.neusoft.model.article.dtos.ArticleDto;
import com.neusoft.model.article.pojos.ApArticle;
import com.neusoft.model.article.pojos.ApArticleConfig;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class IArticleClientFallback implements IArticleClient {

    /**
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }


    /**
     * @param apArticle
     * @return
     */
    @Override
    public ResponseResult deleteArticle(ApArticle apArticle) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"删除数据失败");
    }
}
