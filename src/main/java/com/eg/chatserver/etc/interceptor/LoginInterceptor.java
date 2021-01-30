package com.eg.chatserver.etc.interceptor;

import com.eg.chatserver.utils.Constants;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截器
 *
 * @Author makewheels
 * @Time 2021.01.30 00:46:12
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //检查loginToken
        String loginToken = request.getHeader(Constants.KEY_LOGIN_TOKEN);
        //从redis里，通过loginToken获取user
        //如果校验通过，放行
        //如果校验token没通过，拦截
        return false;
    }
}
