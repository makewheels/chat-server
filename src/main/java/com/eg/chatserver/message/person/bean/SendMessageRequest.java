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
    @ApiModelProperty(value = "会话id", required = true, example = "conv16202bab2ab14ea1aa42d741c4cc60a8")
    private String conversationId;
    @ApiModelProperty(value = "消息类型", required = true, example = MessageType.TEXT)
    private String messageType;
    @ApiModelProperty(value = "消息内容", example = "hello baby!")
    private String content;

    @ApiModelProperty(value = "原始文件名", example = "IMG_001.jpg")
    private String originalFilename;
    @ApiModelProperty(value = "文件md5", example = "69D266E25891BFE24665ABCB9244B7AB")
    private String md5;
    @ApiModelProperty(value = "文件大小", example = "68689920")
    private Long size;

    @ApiModelProperty(value = "音视频时长，单位毫秒", example = "3520")
    private Long duration;

    @ApiModelProperty(value = "图片宽度", example = "1920")
    private Integer imageWidth;
    @ApiModelProperty(value = "图片高度", example = "1080")
    private Integer imageHeight;
}
