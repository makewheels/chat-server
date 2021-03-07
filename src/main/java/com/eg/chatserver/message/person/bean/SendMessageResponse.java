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

    @ApiModelProperty(value = "会话id", example = "conv7e4c9979a43c45748f6ae030e9b6350d")
    private String conversationId;

    private String fromUserId;

    private String toUserId;

    private Date createTime;

    @ApiModelProperty(value = "对象存储名，为了让客户端上传时用", example = "conv7e4c9979a43c45748f6ae030e9b6350d")
    private String objectName;
    @ApiModelProperty(value = "是否需要上传，因为可能md5已经存在，那客户端不用再重复上传，" +
            "但是这也是一条新的消息，需要对方拉取", example = "true")
    private Boolean isNeedUpload;
}
