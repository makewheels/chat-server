package com.eg.chatserver.user;

import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.login.LoginRequest;
import com.eg.chatserver.user.login.LoginResponse;
import com.eg.chatserver.user.register.RegisterRequest;
import com.eg.chatserver.user.register.RegisterResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
@Api(tags = "用户 Controller")
public class UserController {
    @Resource
    private UserAccountService userAccountService;

    /**
     * 注册
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public Result<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
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
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        if (StringUtils.isBlank(loginRequest.getLoginName())
                || StringUtils.isBlank(loginRequest.getPassword())) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        return userAccountService.login(loginRequest);
    }

}
