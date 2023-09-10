package com.neusoft.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ap_user_follow")
@Builder
public class ApUserFollow {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 关注作者ID
     */
    @TableField("follow_id")
    private Integer followId;

    /**
     * 粉丝昵称
     */
    @TableField("follow_name")
    private String followName;

    /**
     * 关注度 0 偶尔感兴趣 1 一般 2 经常
     */
    @TableField("level")
    private Short level;

    /**
     * 是否动态通知
     */
    @TableField("is_notice")
    private Short isNotice;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
}
