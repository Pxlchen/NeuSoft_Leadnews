package com.neusoft.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.article.pojos.ApArticleConfig;

import java.util.Map;

public interface ApArticleConfigService extends IService<ApArticleConfig> {

    /**
     * 修改文章
     * @param map
     */
    void updateByMap(Map map);
}
