package com.eg.chatserver.conversation;

import com.eg.chatserver.bean.Conversation;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.mapper.ConversationMapper;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.conversation.bean.CreateConversationRequest;
import com.eg.chatserver.conversation.bean.CreateConversationResponse;
import com.eg.chatserver.utils.Constants;
import com.eg.chatserver.utils.UuidUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:48:54
 */
@Service
public class ConversationService {
    @Resource
    private ConversationMapper conversationMapper;

    /**
     * 生成会话id
     *
     * @return
     */
    public String generateConversationId() {
        return "conv" + UuidUtil.getRandomUUid()
                + RandomStringUtils.randomAlphanumeric(8);
    }

    /**
     * 创建会话
     *
     * @param user
     * @param createConversationRequest
     * @return
     */
    public Result<CreateConversationResponse> create(
            User user, CreateConversationRequest createConversationRequest) {
        String type = createConversationRequest.getType();
        if (type.equals(Constants.CONVERSATION_TYPE_PERSON)) {
            return createPersonConversation(user, createConversationRequest);
        } else if (type.equals(Constants.CONVERSATION_TYPE_GROUP)) {
            return createGroupConversation(user, createConversationRequest);
        } else {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
    }

    /**
     * 创建个人会话
     *
     * @param user
     * @param createConversationRequest
     * @return
     */
    private Result<CreateConversationResponse> createPersonConversation(
            User user, CreateConversationRequest createConversationRequest) {
        //根据userId查找用户
        String targetId = createConversationRequest.getTargetId();
        Conversation conversation = new Conversation();
        conversation.setConversationId(generateConversationId());
        conversation.setUserId(user.getUserId());

        conversation.setType(createConversationRequest.getType());
        Date date = new Date();
        conversation.setUpdateTime(date);
        conversation.setCreateTime(date);
        return null;
    }

    /**
     * 创建群组会话
     *
     * @param user
     * @param createConversationRequest
     * @return
     */
    private Result<CreateConversationResponse> createGroupConversation(
            User user, CreateConversationRequest createConversationRequest) {
        return Result.error(ErrorCode.NOT_SUPPORT);
    }

}
