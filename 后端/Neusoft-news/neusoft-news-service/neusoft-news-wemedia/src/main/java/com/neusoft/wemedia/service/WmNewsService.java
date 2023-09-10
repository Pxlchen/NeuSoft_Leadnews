package com.neusoft.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.NewsAuthDto;
import com.neusoft.model.wemedia.dtos.WmNewsDto;
import com.neusoft.model.wemedia.dtos.WmNewsEnableDto;
import com.neusoft.model.wemedia.dtos.WmNewsPageReqDto;
import com.neusoft.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {


    /**
     * 条件查询文章列表
     * @param dto
     * @return
     */
    ResponseResult findList(WmNewsPageReqDto dto);

    /**
     * 发布修改文章或保存为草稿
     * @param dto
     * @return
     */
    public ResponseResult submitNews(WmNewsDto dto);

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    ResponseResult queryById(Integer id);

    /**
     * 文章删除
     * @param id
     * @return
     */
    ResponseResult deleteNewsById(Integer id);

    /**
     * 文章上下架
     * @param dto
     * @return
     */
    ResponseResult downOrUp(WmNewsEnableDto dto);

    /**
     * 查询文章列表
     * @param dto
     * @return
     */
    ResponseResult AdQueryList(NewsAuthDto dto);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    ResponseResult queryOne(Integer id);

    /**
     * 文章审核失败
     * @param dto
     * @return
     */
    ResponseResult authFail(NewsAuthDto dto);

    /**
     * 文章审核成功（人工）
     * @param dto
     * @return
     */
    ResponseResult authPass(NewsAuthDto dto);
}
