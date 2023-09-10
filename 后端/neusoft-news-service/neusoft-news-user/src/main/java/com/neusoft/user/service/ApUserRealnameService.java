package com.neusoft.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.user.dtos.AuthDto;
import com.neusoft.model.user.pojos.ApUserRealname;
import org.springframework.web.bind.annotation.RestController;

public interface ApUserRealnameService extends IService<ApUserRealname> {

    /**
     * 查询列表
     * @param dto
     * @return
     */
    ResponseResult findList(AuthDto dto);

    /**
     * 审核失败
     * @param dto
     * @return
     */
    ResponseResult authFail(AuthDto dto);

    /**
     * 审核成功
     * @param dto
     * @return
     */
    ResponseResult authPass(AuthDto dto);
}
