package com.eg.chatserver.user;

import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.register.RegisterRequest;
import com.eg.chatserver.user.register.RegisterResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("user")
@Api(tags = "用户 Controller")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 注册
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public Result<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

}
