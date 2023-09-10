package com.neusoft.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.model.user.pojos.ApUserFan;
import com.neusoft.user.mapper.ApUserFanMapper;
import com.neusoft.user.service.ApUserFanService;
import com.neusoft.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ApUserFanServiceImpl extends ServiceImpl<ApUserFanMapper, ApUserFan>  implements ApUserFanService {

}
