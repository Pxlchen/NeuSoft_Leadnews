package com.neusoft.wemedia.controller.v1;

import com.neusoft.common.constants.WemediaConstants;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.WmMaterialDto;
import com.neusoft.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    @Autowired
    private WmMaterialService wmMaterialService;

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);
    }

    /**
     * 返回查询列表
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto dto){
        return wmMaterialService.findList(dto);
    }

    /**
     * 图片删除
     * @param id
     * @return
     */
    @GetMapping("/del_picture/{id}")
    public ResponseResult deletePicture(@PathVariable Integer id){
        return wmMaterialService.deletePicture(id);
    }

    /**
     * 取消收藏素材
     * @param id
     * @return
     */
    @GetMapping("/cancel_collect/{id}")
    public ResponseResult cancelCollect(@PathVariable Integer id){
        return wmMaterialService.updateCollect(id, WemediaConstants.CANCEL_COLLECT_MATERIAL);
    }

    /**
     * 收藏素材
     * @param id
     * @return
     */
    @GetMapping("/collect/{id}")
    public ResponseResult materialCollect(@PathVariable Integer id){
        return wmMaterialService.updateCollect(id, WemediaConstants.COLLECT_MATERIAL);
    }
}
