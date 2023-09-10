package com.neusoft.wemedia.controller.v1;

import com.neusoft.model.admin.dtos.AdSensitiveDto;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.SensitiveDto;
import com.neusoft.wemedia.service.WmSensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensitive")
public class WmSensitiveController {
    @Autowired
    private WmSensitiveService wmSensitiveService;

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public ResponseResult deleteById(@PathVariable Integer id) {
        return wmSensitiveService.deleteById(id);
    }

    /**
     * 查看敏感词
     *
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody SensitiveDto dto) {
        return wmSensitiveService.findList(dto);
    }

    /**
     * 保存敏感词
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AdSensitiveDto dto){
        return  wmSensitiveService.saveSensitive(dto);
    }

    /**
     * 修改敏感词
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody AdSensitiveDto dto){
        return wmSensitiveService.updateSensitive(dto);
    }
}
