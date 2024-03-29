package com.eg.chatserver.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "注册用户")
public class RegisterRequest {
    @ApiModelProperty(value = "登陆名", example = "Name1")
    private String loginName;
    @ApiModelProperty(value = "密码", example = "111")
    private String password;
    @ApiModelProperty(value = "极光推送id", example = "100d855909e0fa5a708")
    private String jpushRegistrationId;
}
