package com.neusoft.model.wemedia.dtos;

import com.neusoft.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class WmMaterialDto extends PageRequestDto {

    /**
     * 1 收藏
     * 0 不收藏
     */
    private short isCollection;
    //页码
    private Integer page;
    //页码大小
    private Integer size;

    @Override
    public void checkParam() {
        super.checkParam();
    }


}
