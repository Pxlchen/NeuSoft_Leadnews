package com.neusoft.utils.thread;

import com.neusoft.model.admin.pojos.AdUser;

public class AdThreadLocalUtil {

    private final static ThreadLocal<AdUser> AD_USER_THREAD_LOCAL = new ThreadLocal<>();
    //存入线程
    public static void setUser(AdUser adUser){
        AD_USER_THREAD_LOCAL.set(adUser);
    }

    //从线程中获取
    public static AdUser getUser(){
        return AD_USER_THREAD_LOCAL.get();
    }

    //清理
    public static void clear(){
        AD_USER_THREAD_LOCAL.remove();
    }
}
