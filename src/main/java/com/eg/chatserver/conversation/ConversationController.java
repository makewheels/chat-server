package com.eg.chatserver.conversation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:48:35
 */
@RestController
@RequestMapping("conversation")
public class ConversationController {
    @Resource
    private ConversationService conversationService;



}
