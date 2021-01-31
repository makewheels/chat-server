package com.eg.chatserver.user;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.bean.LoginRequest;
import com.eg.chatserver.user.bean.RegisterRequest;
import com.eg.chatserver.user.bean.SearchLoginNameResponse;
import com.eg.chatserver.user.bean.UserInfoResponse;
import com.eg.chatserver.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
@Api(tags = "用户 Controller")
public class UserController {
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 注册
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public Result<UserInfoResponse> register(@RequestBody RegisterRequest registerRequest) {
        String loginName = registerRequest.getLoginName();
        String password = registerRequest.getPassword();
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        return userAccountService.register(registerRequest);
    }

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public Result<UserInfoResponse> login(@RequestBody LoginRequest loginRequest) {
        if (StringUtils.isBlank(loginRequest.getLoginName())
                || StringUtils.isBlank(loginRequest.getPassword())) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        return userAccountService.login(loginRequest);
    }

    /**
     * 登出
     */
    @PostMapping("logout")
    @ApiOperation(value = "用户登出")
    public Result<Void> logout(HttpServletRequest httpServletRequest) {
        User user = userAccountService.getUserByRequest(httpServletRequest);
        return userAccountService.logout(user);
    }

    /**
     * 检查loginToken
     *
     * @param request
     * @return
     */
    @PostMapping("checkLoginToken")
    @ApiOperation(value = "检查loginToken")
    public Result<Void> checkLoginToken(HttpServletRequest request) {
        //从header中获取loginToken
        String loginToken = request.getHeader(Constants.KEY_LOGIN_TOKEN);
        //检查loginToken
        boolean checkAndLoadLoginToken = userAccountService.checkAndLoadLoginToken(loginToken);
        //校验通过
        if (checkAndLoadLoginToken) {
            return Result.ok();
        } else {
            //校验未通过，去登录
            return Result.error(ErrorCode.CHECK_LOGIN_TOKEN_ERROR);
        }
    }

    /**
     * 根据登录名精准查询用户
     *
     * @return
     */
    @PostMapping("searchUserByLoginName")
    @ApiOperation(value = "根据登录名精准搜索用户")
    public Result<SearchLoginNameResponse> searchUserByLoginName(
            @ApiParam(name = "loginName", value = "登录名", example = "Name")
            @RequestParam String loginName) {
        User user = userInfoService.searchUserByLoginName(loginName);
        if (user == null) {
            return Result.error(ErrorCode.SEARCH_USER_LOGIN_NAME_NOT_EXIST);
        } else {
            SearchLoginNameResponse searchLoginNameResponse = new SearchLoginNameResponse();
            searchLoginNameResponse.setUserId(user.getUserId());
            searchLoginNameResponse.setLoginName(user.getLoginName());
            searchLoginNameResponse.setHeadImageUrl(user.getHeadImageUrl());
            return Result.ok(searchLoginNameResponse);
        }
    }
}
