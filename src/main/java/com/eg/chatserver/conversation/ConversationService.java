package com.eg.chatserver.conversation;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.mapper.ConversationMapper;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.conversation.bean.CreateConversationRequest;
import com.eg.chatserver.conversation.bean.CreateConversationResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:48:54
 */
@Service
public class ConversationService {
    @Resource
    private ConversationMapper conversationMapper;

    /**
     * 创建会话
     * @param user
     * @param createConversationRequest
     * @return
     */
    public Result<CreateConversationResponse> create(
            User user, CreateConversationRequest createConversationRequest) {

        return null;
    }
}
