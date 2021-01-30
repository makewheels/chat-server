package com.eg.chatserver.etc.interceptor;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.UserAccountService;
import com.eg.chatserver.user.UserRedisService;
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
    private UserRedisService userRedisService;
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
        System.out.println(request.getRequestURI());
        //从header里获取loginToken
        String loginToken = request.getHeader(Constants.KEY_LOGIN_TOKEN);
        //先从redis里取
        boolean isLoginTokenExist = userRedisService.isLoginTokenExist(loginToken);
        //如果redis里存在，那就放行
        if (isLoginTokenExist) {
            return true;
        } else {
            //如果redis里不存在，先查数据库
            //这种情况，可能是因为，redis过期了，也可能是服务重启了
            User user = userAccountService.findUserByLoginTokenFromSql(loginToken);
            //如果从数据库找到了user，那么存入redis
            if (user != null) {
                userRedisService.setUserByLoginToken(loginToken, user);
                //放行
                return true;
            } else {
                //如果数据库也没有，哈哈，那这哥们不对劲，给他返回错误信息，告诉他要登录
                //正常来说这种情况是不会出现的
                log.error("interceptor loginToken check error, loginToken = {}", loginToken);
                response.setCharacterEncoding("utf-8");
                Result<Object> error = Result.error(ErrorCode.NEED_LOGIN);
                response.getWriter().write(JSON.toJSONString(error));
                //拦截
                return false;
            }
        }
    }

}
