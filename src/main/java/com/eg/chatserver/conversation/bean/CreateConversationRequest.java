package com.eg.chatserver.conversation.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:54:51
 */
@Data
@ApiModel(value = "申请创建会话")
public class CreateConversationRequest {

    @ApiModelProperty(value = "要和谁创建会话，可以是用户id，可以是群组id", example = "targetId")
    private String targetId;
    @ApiModelProperty(value = "类型，是人还是群", example = "person")
    private String type;

}
