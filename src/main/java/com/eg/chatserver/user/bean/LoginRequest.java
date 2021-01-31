package com.eg.chatserver.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.01.30 12:21:23
 */
@Data
@ApiModel(value = "登录")
public class LoginRequest {
    @ApiModelProperty(value = "登陆名", example = "Name1")
    private String loginName;
    @ApiModelProperty(value = "密码", example = "111")
    private String password;
    @ApiModelProperty(value = "极光推送id", example = "100d855909e0fa5a708")
    private String jpushRegistrationId;
}

