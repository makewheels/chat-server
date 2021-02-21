package com.eg.chatserver.user.bean.phone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.02.21 23:54:34
 */
@Data
@ApiModel(value = "修改手机：提交验证码请求体")
public class SubmitVerificationCodeRequest {
    @ApiModelProperty(value = "验证码", example = "955019")
    private String verificationCode;
}
