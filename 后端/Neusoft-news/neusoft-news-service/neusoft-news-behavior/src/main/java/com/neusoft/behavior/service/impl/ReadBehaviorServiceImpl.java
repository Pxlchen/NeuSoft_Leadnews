package com.neusoft.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.neusoft.behavior.service.ReadBehaviorService;
import com.neusoft.common.constants.HotArticleConstants;
import com.neusoft.common.constants.UserBehaviorConstants;
import com.neusoft.common.redis.CacheService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.mess.UpdateArticleMess;
import com.neusoft.model.user.dtos.ReadBehaviorDto;
import com.neusoft.model.user.pojos.ApUser;
import com.neusoft.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ReadBehaviorServiceImpl implements ReadBehaviorService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * 记录用户阅读行为
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult readBehavior(ReadBehaviorDto dto) {
        //判断参数是否为空
        if (dto == null || dto.getArticleId() == null || dto.getCount() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取用户id
        ApUser user = AppThreadLocalUtil.getUser();
        //判断是否登录
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        //判断是否为第一次阅读
        String dtojson = (String)cacheService.hGet(UserBehaviorConstants.READ_BEHAVIOR+dto.getArticleId()
                ,user.getId().toString());
        if (dtojson == null){
            cacheService.hPut(UserBehaviorConstants.READ_BEHAVIOR+dto.getArticleId(),user.getId().toString()
                    , JSON.toJSONString(dto));
        }else {//不为第一次阅读，阅读次数+1
            ReadBehaviorDto readBehaviorDto = JSON.parseObject(dtojson, ReadBehaviorDto.class);
            readBehaviorDto.setCount(readBehaviorDto.getCount()+dto.getCount());
            cacheService.hPut(UserBehaviorConstants.READ_BEHAVIOR+dto.getArticleId(),user.getId().toString()
                    , JSON.toJSONString(readBehaviorDto));
        }

        //发送消息，数据聚合
        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(dto.getArticleId());
        mess.setType(UpdateArticleMess.UpdateArticleType.VIEWS);
        mess.setAdd(1);
        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC,JSON.toJSONString(mess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
