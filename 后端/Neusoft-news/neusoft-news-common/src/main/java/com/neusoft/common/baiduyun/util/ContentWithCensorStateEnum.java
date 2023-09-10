package com.neusoft.common.baiduyun.util;

/**
 * 内容审核状态
 */
public enum ContentWithCensorStateEnum {
    /**
     * 正常状态
     */
    ADD,

    /**
     * 删除状态
     */
    REMOVE,

    /**
     * Ai审核不通过
     */
    CENSOR_FAIL,

    /**
     * Ai审核疑似不通过
     */
    CENSOR_SUSPECT,

    /**
     * Ai审核错误
     */
    CENSOR_ERROR,

    /**
     * 人工审核不通过
     */
    BLOCK
}

