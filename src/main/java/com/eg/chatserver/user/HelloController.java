package com.eg.chatserver.user;

import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.register.RegisterResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author makewheels
 * @Time 2021.01.30 16:02:55
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @RequestMapping("sayHello")
    public Result<RegisterResponse> sayHello() {
        return Result.ok(new RegisterResponse());
    }
}
