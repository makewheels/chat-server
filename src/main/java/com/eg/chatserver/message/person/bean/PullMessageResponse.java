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

    @ApiModelProperty(value = "文件地址", example = "https://chat-develop-1253319037.cos.ap-beijing.myqcloud.com/audio/user0ac2a32e94834c9b9d1dec3cb7f64623/filee0a196bd450a41b683e28310d6cb166e.wav?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDGNHKCnksd8OPYgeVHp9HBO50iKhou5Tj%26q-sign-time%3D1619701377%3B1619701977%26q-key-time%3D1619701377%3B1619701977%26q-header-list%3Dhost%26q-url-param-list%3D%26q-signature%3Daf52d57ca1952d41a00bea2bc5901b6fedd81b21")
    private String fileUrl;
    private String fileName;
    private String imagePreviewUrl;

}
