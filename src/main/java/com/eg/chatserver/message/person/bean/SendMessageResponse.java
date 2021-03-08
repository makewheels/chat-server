package com.eg.chatserver.message.person.bean;

import com.eg.chatserver.oss.OssCredential;
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
    private String messageId;
    private String conversationId;
    private String fromUserId;
    private String toUserId;
    private Date createTime;

    //以下只有在上传文件的时候才调用
    private String bucket;
    private String object;

    @ApiModelProperty(value = "是否需要上传，因为可能md5已经存在，那客户端不用再重复上传，" +
            "但是这也是一条新的消息，需要对方拉取", example = "true")
    private Boolean isNeedUpload;

    private OssCredential ossCredential;

}
