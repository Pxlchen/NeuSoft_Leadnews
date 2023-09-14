package com.neusoft.wemedia.controller.v1;

import com.neusoft.common.constants.WemediaConstants;
import com.neusoft.model.common.dtos.PageRequestDto;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.WmMaterialDto;
import com.neusoft.model.wemedia.dtos.collectDto;
import com.neusoft.wemedia.service.WmMaterialService;
import org.apache.ibatis.annotations.Param;
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
    public ResponseResult uploadPicture(@RequestParam("file") MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);
    }

    /**
     * 返回查询列表
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody PageRequestDto dto){
        return wmMaterialService.findList(dto);
    }

    /**
     * 图片删除
     * @param dto
     * @return
     */
    @PostMapping("/del_picture")
    public ResponseResult deletePicture(@RequestBody collectDto dto){
        return wmMaterialService.deletePicture(dto.getCollectId());
    }

    /**
     * 取消收藏素材
     * @param dto
     * @return
     */
    @PostMapping("/cancel_collect")
    public ResponseResult cancelCollect(@RequestBody collectDto dto){
        return wmMaterialService.updateCollect(dto.getCollectId(), WemediaConstants.CANCEL_COLLECT_MATERIAL);
    }


    /**
     * 收藏素材
     * @param dto
     * @return
     */
    @PostMapping("/collect")
    public ResponseResult materialCollect(@RequestBody collectDto dto){
        return wmMaterialService.updateCollect(dto.getCollectId(), WemediaConstants.COLLECT_MATERIAL);
    }
}
