package com.neusoft.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.neusoft.article.service.ApCollectionService;
import com.neusoft.common.constants.HotArticleConstants;
import com.neusoft.common.constants.UserBehaviorConstants;
import com.neusoft.common.redis.CacheService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.mess.UpdateArticleMess;
import com.neusoft.model.user.dtos.CollectionBehaviorDto;
import com.neusoft.model.user.pojos.ApUser;
import com.neusoft.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApCollectionServiceImpl implements ApCollectionService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * 文章收藏
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult collectionBehavior(CollectionBehaviorDto dto) {
        //判断参数是否正确
        if (dto == null || dto.getOperation() < 0 || dto.getOperation() > 1){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取用户id
        ApUser user = AppThreadLocalUtil.getUser();
        //判断是否登录
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(dto.getEntryId());
        mess.setType(UpdateArticleMess.UpdateArticleType.COLLECTION);

        if (dto.getOperation() == 0){//添加收藏
            cacheService.hPut(UserBehaviorConstants.COLLECTION_BEHAVIOR + dto.getEntryId(),
                    user.getId().toString(), JSON.toJSONString(dto));
            mess.setAdd(1);
        }else {//取消收藏
            cacheService.hDelete(UserBehaviorConstants.COLLECTION_BEHAVIOR + dto.getEntryId(),
                    user.getId().toString());
            mess.setAdd(-1);
        }

        //发送消息，数据聚合
        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC,JSON.toJSONString(mess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
