package com.neusoft.utils.thread;

import com.neusoft.model.wemedia.pojos.WmUser;

public class WmThreadLocalUtil {

    private final static ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();
    //存入线程
    public static void setUser(WmUser wmUser){
        WM_USER_THREAD_LOCAL.set(wmUser);
    }

    //从线程中获取
    public static WmUser getUser(){
        return WM_USER_THREAD_LOCAL.get();
    }

    //清理
    public static void clear(){
        WM_USER_THREAD_LOCAL.remove();
    }
}
