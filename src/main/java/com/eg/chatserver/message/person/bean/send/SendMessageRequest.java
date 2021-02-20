package com.eg.chatserver.message.person.bean.send;

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
    @ApiModelProperty(value = "会话id", required = true, example = "conv16202bab2ab14ea1aa42d741c4cc60a8")
    private String conversationId;
    @ApiModelProperty(value = "消息类型", required = true, example = MessageType.TEXT)
    private String messageType;
    @ApiModelProperty(value = "消息内容", example = "hello baby!")
    private String content;

    @ApiModelProperty(value = "音频时长，单位毫秒", example = "3520")
    private Integer audioDuration;
    @ApiModelProperty(value = "原始文件名", example = "IMG_001.jpg")
    private String originalFilename;
}
