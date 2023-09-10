package com.neusoft.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.neusoft.behavior.service.LikeBehaviorService;
import com.neusoft.common.constants.HotArticleConstants;
import com.neusoft.common.constants.UserBehaviorConstants;
import com.neusoft.common.redis.CacheService;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.mess.UpdateArticleMess;
import com.neusoft.model.user.dtos.LikesBehaviorDto;
import com.neusoft.model.user.dtos.UnLikesBehaviorDto;
import com.neusoft.model.user.pojos.ApUser;
import com.neusoft.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeBehaviorServiceImpl implements LikeBehaviorService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * 点赞与取消点赞
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult likesBehavior(LikesBehaviorDto dto) {
        //判断参数是否正确
        if (dto == null || dto.getOperation() < 0 || dto.getOperation() > 1){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        if (dto.getType() < 0 || dto.getType() > 2){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取用户id
        ApUser user = AppThreadLocalUtil.getUser();
        //判断是否登录
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(dto.getArticleId());
        mess.setType(UpdateArticleMess.UpdateArticleType.LIKES);


        if (dto.getOperation() == 0){
            //用户点赞,查看是否已经点赞
            Object obj = cacheService.hGet(UserBehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId()
                    , user.getId().toString());
            if (obj != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"已点赞");
            }
            cacheService.hPut(UserBehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId(),
                    user.getId().toString(), JSON.toJSONString(dto));
            //文章点赞量加一
            mess.setAdd(1);
        }else {//用户取消点赞
            cacheService.hDelete(UserBehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId(),
                    user.getId().toString());
            //文章点赞量减一
            mess.setAdd(-1);
        }

        //发送消息，数据聚合
        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC,JSON.toJSONString(mess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 不喜欢与取消不喜欢
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult unLikesBehavior(UnLikesBehaviorDto dto) {

        //判断参数是否正确
        if (dto == null || dto.getType() < 0 || dto.getType() > 1){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取用户id
        ApUser user = AppThreadLocalUtil.getUser();
        //判断是否登录
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        if (dto.getType() == 0){ //用户不喜欢，添加不喜欢记录
            cacheService.hPut(UserBehaviorConstants.UN_LIKE_BEHAVIOR + dto.getArticleId(),
                    user.getId().toString(), JSON.toJSONString(dto));
        }else {//删除用户不喜欢记录
            cacheService.hDelete(UserBehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId(),
                    user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
