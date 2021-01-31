package com.eg.chatserver.etc.interceptor;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.UserAccountService;
import com.eg.chatserver.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @Author makewheels
 * @Time 2021.01.30 00:46:12
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserAccountService userAccountService;

    /**
     * 经过登录拦截器之后的效果是
     * 再执行后面的逻辑的时候，可以保证redis里已经有该user了
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //从header中获取loginToken
        String loginToken = request.getHeader(Constants.KEY_LOGIN_TOKEN);
        //检查loginToken
        boolean checkAndLoadLoginToken = userAccountService.checkAndLoadLoginToken(loginToken);
        if (!checkAndLoadLoginToken) {
            //如果不存在此loginToken，返回错误信息，告诉他去登录
            log.error("interceptor loginToken check error, requestURI = {}, loginToken = {}"
                    , request.getRequestURI(), loginToken);
            response.setCharacterEncoding("utf-8");
            Result<Void> error = Result.error(ErrorCode.NEED_LOGIN);
            response.getWriter().write(JSON.toJSONString(error));
        }
        return checkAndLoadLoginToken;
    }

}
