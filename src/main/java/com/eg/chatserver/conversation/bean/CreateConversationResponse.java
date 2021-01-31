package com.eg.chatserver.conversation.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:56:30
 */
@Data
public class CreateConversationResponse {
    @ApiModelProperty(value = "会话id", example = "conversationId")
    private String conversationId;
    @ApiModelProperty(value = "要和谁创建会话，可以是用户id，可以是群组id",
            example = "user14f573dffc0142c38be97c9360fbbac3")
    private String targetId;
    @ApiModelProperty(value = "类型，是人还是群", example = "person")
    private String type;
}
