package com.neusoft.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neusoft.model.article.dtos.ArticleHomeDto;
import com.neusoft.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    /**
     * 加载文字列表
     * @param dto
     * @param type 1 加载更多 2加载最新
     * @return
     */
    public List<ApArticle> loadArticleList(ArticleHomeDto dto, Short type);


    /**
     * 查询前五天文章数据
     * @param dayParam
     * @return
     */
    public List<ApArticle> findArticleListByLast5days(@Param("dayParam")Date dayParam);
}
