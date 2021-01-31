package com.eg.chatserver.message.person.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.01.31 16:05:16
 */
@Data
@ApiModel(value = "发送对人的消息")
public class SendMessageResponse {
    @ApiModelProperty(value = "消息id", example = "100d855909e0fa5a708")
    private String messageId;

    @ApiModelProperty(value = "会话id", required = true, example = "conversationId")
    private String conversationId;
    @ApiModelProperty(value = "消息类型", required = true, example = "text")
    private String messageType;
}
