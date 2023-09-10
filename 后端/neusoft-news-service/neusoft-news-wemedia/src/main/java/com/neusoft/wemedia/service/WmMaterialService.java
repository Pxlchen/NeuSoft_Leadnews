package com.neusoft.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.WmMaterialDto;
import com.neusoft.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    public ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 素材列表查询
     * @param dto
     * @return
     */
    public ResponseResult findList(WmMaterialDto dto);


    /**
     * 图片删除
     * @param id
     * @return
     */
    ResponseResult deletePicture(Integer id);

    /**
     * 修改素材收藏
     * @param id
     * @return
     */
    ResponseResult updateCollect(Integer id,Short type);
}