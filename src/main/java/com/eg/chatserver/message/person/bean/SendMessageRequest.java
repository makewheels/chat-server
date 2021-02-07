package com.eg.chatserver.message.person.bean;

import com.eg.chatserver.message.MessageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.01.31 16:05:16
 */
@Data
@ApiModel(value = "发送对人的消息")
public class SendMessageRequest {
    @ApiModelProperty(value = "会话id", required = true, example = "conv7e4c9979a43c45748f6ae030e9b6350d")
    private String conversationId;
    @ApiModelProperty(value = "消息类型", required = true, example = MessageType.TEXT)
    private String messageType;
    @ApiModelProperty(value = "消息内容", example = "hello baby!")
    private String content;

    @ApiModelProperty(value = "原始文件名，发送文本时不需要", example = "IMG_001.jpg")
    private String originalFileName;

}
