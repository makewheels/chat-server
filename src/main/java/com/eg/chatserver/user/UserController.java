package com.eg.chatserver.user;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.bean.*;
import com.eg.chatserver.user.bean.phone.GetVerificationCodeRequest;
import com.eg.chatserver.user.bean.phone.SubmitVerificationCodeRequest;
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

    @PostMapping("register")
    @ApiOperation(value = "注册")
    public Result<UserInfoResponse> register(@RequestBody RegisterRequest registerRequest) {
        String loginName = registerRequest.getLoginName();
        String password = registerRequest.getPassword();
        String jpushRegistrationId = registerRequest.getJpushRegistrationId();
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)
                || StringUtils.isEmpty(jpushRegistrationId)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        return userAccountService.register(registerRequest);
    }

    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result<UserInfoResponse> login(@RequestBody LoginRequest loginRequest) {
        String loginName = loginRequest.getLoginName();
        String password = loginRequest.getPassword();
        String jpushRegistrationId = loginRequest.getJpushRegistrationId();
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)
                || StringUtils.isEmpty(jpushRegistrationId)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        return userAccountService.login(loginRequest);
    }

    @PostMapping("logout")
    @ApiOperation(value = "登出")
    public Result<Void> logout(HttpServletRequest httpServletRequest) {
        User user = userAccountService.getUserByRequest(httpServletRequest);
        return userAccountService.logout(user);
    }

    @PostMapping("checkLoginToken")
    @ApiOperation(value = "检查loginToken")
    public Result<Void> checkLoginToken(HttpServletRequest request) {
        //从header中获取loginToken
        String loginToken = request.getHeader(Constants.KEY.LOGIN_TOKEN);
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

    @PostMapping("getUserInfo")
    @ApiOperation(value = "获取用户信息")
    public Result<UserInfoResponse> getUserInfo(HttpServletRequest request) {
        User user = userAccountService.getUserByRequest(request);
        return userAccountService.getUserInfo(user);
    }

    @PostMapping("searchUserByLoginName")
    @ApiOperation(value = "根据登录名精准搜索用户")
    public Result<SearchLoginNameResponse> searchUserByLoginName(
            @ApiParam(name = "loginName", value = "登录名", example = "Name2")
            @RequestParam String loginName) {
        if (StringUtils.isBlank(loginName)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        User user = userInfoService.searchUserByLoginName(loginName);
        if (user == null) {
            return Result.error(ErrorCode.SEARCH_USER_LOGIN_NAME_NOT_EXIST);
        } else {
            SearchLoginNameResponse searchLoginNameResponse = new SearchLoginNameResponse();
            searchLoginNameResponse.setUserId(user.getUserId());
            searchLoginNameResponse.setLoginName(user.getLoginName());
            searchLoginNameResponse.setNickName(user.getNickname());
            searchLoginNameResponse.setHeadImageUrl(user.getHeadImageUrl());
            return Result.ok(searchLoginNameResponse);
        }
    }

    @PostMapping("modifyNickname")
    @ApiOperation(value = "修改昵称")
    public Result<Void> modifyNickname(
            @ApiParam(name = "newNickname", value = "修改昵称", example = "new name")
            @RequestParam String newNickname, HttpServletRequest request) {
        if (StringUtils.isBlank(newNickname)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        User user = userAccountService.getUserByRequest(request);
        return userInfoService.modifyNickname(user, newNickname);
    }

    @PostMapping("modifyPassword")
    @ApiOperation(value = "修改密码")
    public Result<Void> modifyPassword(
            @RequestBody ModifyPasswordRequest modifyPasswordRequest, HttpServletRequest request) {
        if (modifyPasswordRequest == null
                || StringUtils.isBlank(modifyPasswordRequest.getOldPassword())
                || StringUtils.isBlank(modifyPasswordRequest.getNewPassword())) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        User user = userAccountService.getUserByRequest(request);
        return userInfoService.modifyPassword(user, modifyPasswordRequest);
    }

    @PostMapping("modifyPhone/getVerificationCode")
    @ApiOperation(value = "修改手机：请求验证码")
    public Result<Void> modifyPhone_getVerificationCode(
            HttpServletRequest request,
            @RequestBody GetVerificationCodeRequest getVerificationCodeRequest) {
        if (StringUtils.isBlank(getVerificationCodeRequest.getPassword())
                || StringUtils.isBlank(getVerificationCodeRequest.getNewPhone())) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        User user = userAccountService.getUserByRequest(request);
        return userAccountService.modifyPhone_getVerificationCode(user, getVerificationCodeRequest);
    }

    @PostMapping("modifyPhone/submitVerificationCode")
    @ApiOperation(value = "修改手机：提交验证码")
    public Result<Void> modifyPhone_submitVerificationCode(
            HttpServletRequest request,
            @RequestBody SubmitVerificationCodeRequest submitVerificationCodeRequest) {
        if (StringUtils.isBlank(submitVerificationCodeRequest.getVerificationCode()))
            return Result.error(ErrorCode.WRONG_PARAM);
        User user = userAccountService.getUserByRequest(request);
        return userAccountService.modifyPhone_submitVerificationCode(user, submitVerificationCodeRequest);
    }
}
