package com.eg.chatserver.user.bean.phone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.02.21 23:37:26
 */
@Data
@ApiModel(value = "修改手机：请求验证码请求体")
public class GetVerificationCodeRequest {
    @ApiModelProperty(value = "密码", example = "1")
    private String password;
    @ApiModelProperty(value = "新手机号", example = "15664886285")
    private String newPhone;
}
