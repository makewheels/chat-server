package com.eg.chatserver.message.person.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.01.31 22:24:13
 */
@Data
@ApiModel(value = "拉取消息请求")
public class MessageIdRequest {
    @ApiModelProperty(value = "消息id", required = true, example = "pmsg9f1dc9519f4f47a0a6904810b0506ec4")
    private String messageId;

}
