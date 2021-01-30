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
    @ApiModelProperty(value = "登陆名", example = "user001")
    private String loginName;
    @ApiModelProperty(value = "密码", example = "passe23rq23r")
    private String password;
}

