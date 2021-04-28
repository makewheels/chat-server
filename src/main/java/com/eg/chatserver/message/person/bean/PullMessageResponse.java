package com.eg.chatserver.message.person.bean;

import com.eg.chatserver.message.MessageType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author makewheels
 * @Time 2021.01.31 23:53:44
 */
@Data
public class PullMessageResponse {
    @ApiModelProperty(value = "消息id", example = "pmsg9f1dc9519f4f47a0a6904810b0506ec4")
    private String messageId;

    @ApiModelProperty(value = "会话id", example = "conv7e4c9979a43c45748f6ae030e9b6350d")
    private String conversationId;

    @ApiModelProperty(value = "消息类型", example = MessageType.TEXT)
    private String messageType;

    private String fromUserId;

    private String toUserId;

    private Boolean isArrive;

    private Date arriveTime;

    private Boolean isRead;

    private Date readTime;

    private Date createTime;

    private String content;

    @ApiModelProperty(value = "音频文件地址", example = "http://s.faef")
    private String fileUrl;

}
