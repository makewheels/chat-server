package com.eg.chatserver.conversation.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author makewheels
 * @Time 2021.02.02 23:07:36
 */
@Data
public class ConversationResponse {
    private String conversationId;

    private String userId;

    private String targetId;

    private String type;

    private String title;
    private String headImageUrl;

    private Integer messageCount;

    private Integer unreadMessageCount;

    private Date updateTime;

    private Date createTime;
}
