package com.eg.chatserver.message.person.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author makewheels
 * @Time 2021.01.31 16:05:16
 */
@Data
@ApiModel(value = "发送对人的消息")
public class SendMessageResponse {
    @ApiModelProperty(value = "消息id", example = "pmsg9f1dc9519f4f47a0a6904810b0506ec4")
    private String messageId;

    @ApiModelProperty(value = "会话id", required = true, example = "conv7e4c9979a43c45748f6ae030e9b6350d")
    private String conversationId;

    private String fromUserId;

    private String toUserId;

    private Date createTime;

}
