package com.eg.chatserver.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.01.30 23:04:37
 */
@Data
@ApiModel(value = "搜索登录名")
public class SearchLoginNameResponse {
    @ApiModelProperty(value = "用户id", example = "userd33b94c6c642422784d6c7b1c3f2e76e")
    private String userId;
    @ApiModelProperty(value = "登陆名", example = "user001")
    private String loginName;
    @ApiModelProperty(value = "头像地址", example = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1587979846,3695073765&fm=26&gp=0.jpg")
    private String headImageUrl;
}
