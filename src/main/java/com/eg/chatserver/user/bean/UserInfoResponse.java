package com.eg.chatserver.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author makewheels
 * @Time 2021.01.30 16:56:09
 */
@Data
@ApiModel(value = "响应用户信息")
public class UserInfoResponse {
    @ApiModelProperty(value = "用户id", example = "userd33b94c6c642422784d6c7b1c3f2e76e")
    private String userId;
    @ApiModelProperty(value = "登陆名", example = "user001")
    private String loginName;
    @ApiModelProperty(value = "昵称", example = "user001")
    private String nickname;
    @ApiModelProperty(value = "头像地址", example = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1587979846,3695073765&fm=26&gp=0.jpg")
    private String headImageUrl;
    @ApiModelProperty(value = "自动登录token", example = "loginTokene5676075cff345f6ac7ca24ff41be4cdn8e1g7n2")
    private String loginToken;
    @ApiModelProperty(value = "手机号", example = "11111111111")
    private String phone;
    @ApiModelProperty(value = "极光注册id", example = "1a0018970a013df1194")
    private String jpushRegistrationId;
    @ApiModelProperty(value = "注册时间", example = "2021-02-04T06:54:36.000+00:00")
    private Date createTime;
}
