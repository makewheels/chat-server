package com.eg.chatserver.user.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.01.30 12:21:38
 */
@Data
@ApiModel(value = "登录响应")
public class LoginResponse {
    @ApiModelProperty(value = "用户id", example = "userd33b94c6c642422784d6c7b1c3f2e76e")
    private String userId;
    @ApiModelProperty(value = "登陆名", example = "user001")
    private String loginName;
    @ApiModelProperty(value = "头像地址", example = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1587979846,3695073765&fm=26&gp=0.jpg")
    private String headImageUrl;
    @ApiModelProperty(value = "自动登录token", example = "loginTokene5676075cff345f6ac7ca24ff41be4cdn8e1g7n2")
    private String loginToken;
}
