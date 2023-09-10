package com.neusoft.admin.interceptor;

import com.neusoft.model.admin.pojos.AdUser;
import com.neusoft.utils.thread.AdThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdTokenInterceptor implements HandlerInterceptor {

    /**
     * 得到header中的管理员用户信息，并且存入到当前线程中
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("userId");
        if (userId != null){
            //存入到当前线程中
            AdUser adUser = new AdUser();
            adUser.setId(Integer.valueOf(userId));
            AdThreadLocalUtil.setUser(adUser);
        }

        return true;
    }

    /**
     * 清理线程中的数据
     * @param request current HTTP request
     * @param response current HTTP response
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AdThreadLocalUtil.clear();//该方法无论是否有异常都会执行
    }
}
