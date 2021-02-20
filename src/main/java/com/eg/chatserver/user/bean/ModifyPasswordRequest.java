package com.eg.chatserver.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.02.20 22:27:51
 */
@Data
@ApiModel(value = "修改密码请求体")
public class ModifyPasswordRequest {
    @ApiModelProperty(value = "原来密码", example = "123456")
    private String oldPassword;
    @ApiModelProperty(value = "新密码", example = "789101112")
    private String newPassword;
}
