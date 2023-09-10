package com.neusoft.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.admin.dtos.AdSensitiveDto;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.SensitiveDto;
import com.neusoft.model.wemedia.pojos.WmSensitive;

public interface WmSensitiveService extends IService<WmSensitive> {

    /**
     * 根据id删除敏感词
     * @param id
     * @return
     */
    ResponseResult deleteById(Integer id);

    /**
     * 查看敏感词
     * @param dto
     * @return
     */
    ResponseResult findList(SensitiveDto dto);

    /**
     * 保存敏感词
     * @param dto
     * @return
     */
    ResponseResult saveSensitive(AdSensitiveDto dto);

    /**
     * 修改敏感词
     * @param dto
     * @return
     */
    ResponseResult updateSensitive(AdSensitiveDto dto);
}
