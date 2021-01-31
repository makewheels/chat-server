package com.eg.chatserver.conversation;

import com.eg.chatserver.bean.mapper.ConversationMapper;
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
}
