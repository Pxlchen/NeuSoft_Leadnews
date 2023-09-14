package com.neusoft.wemedia.controller.v1;


import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.*;
import com.neusoft.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    @Autowired
    private WmNewsService wmNewsService;

    /**
     * 查询文章列表
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmNewsPageReqDto dto){
        return wmNewsService.findList(dto);
    }

    /**
     * 发布修改文章或保存文草稿
     * @param dto
     * @return
     */
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto){
        return wmNewsService.submitNews(dto);
    }

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    @GetMapping("/one/{id}")
    public ResponseResult queryById(@PathVariable Integer id){
        return wmNewsService.queryById(id);
    }

    /**
     * 文章删除
     * @param dto
     * @return
     */
    @PostMapping("/del_news")
    public ResponseResult deleteNewsById(@RequestBody NewsDto dto){
        return wmNewsService.deleteNewsById(dto.getId());
    }

    /**
     * 文章上下架
     * @param dto
     * @return
     */
    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsEnableDto dto){
        return wmNewsService.downOrUp(dto);
    }

    /**
     * 查询文章列表
     * @param dto
     * @return
     */
    @PostMapping("/list_vo")
    public ResponseResult AdQueryList(@RequestBody NewsAuthDto dto){
        return wmNewsService.AdQueryList(dto);
    }

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    @GetMapping("/one_vo/{id}")
    public ResponseResult queryOne(@PathVariable Integer id){
        return wmNewsService.queryOne(id);
    }

    /**
     * 文章审核失败（人工）
     * @param dto
     * @return
     */
    @PostMapping("/auth_fail")
    public ResponseResult authFail(@RequestBody NewsAuthDto dto){
        return wmNewsService.authFail(dto);
    }

    /**
     * 文章审核成功（人工）
     * @param dto
     * @return
     */
    @PostMapping("/auth_pass")
    public ResponseResult authPass(@RequestBody NewsAuthDto dto){
        return wmNewsService.authPass(dto);
    }
}
