package com.eg.chatserver.conversation;

import com.eg.chatserver.bean.Conversation;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.mapper.ConversationMapper;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.conversation.bean.CreateConversationRequest;
import com.eg.chatserver.conversation.bean.CreateConversationResponse;
import com.eg.chatserver.user.UserAccountService;
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
    @Resource
    private UserAccountService userAccountService;

    /**
     * 生成会话id
     *
     * @return
     */
    public String generateConversationId() {
        return "conv" + UuidUtil.getRandomUUid();
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
        //根据userId查找目标用户
        String targetId = createConversationRequest.getTargetId();
        User targetUser = userAccountService.findUserByUserId(targetId);
        //如果目标用户不存在
        if (targetUser == null) {
            return Result.error(ErrorCode.CONVERSATION_CREATE_TARGET_USER_NOT_EXIST);
        }
        //创建会话
        Conversation conversation = new Conversation();
        String conversationId = generateConversationId();
        conversation.setConversationId(conversationId);
        conversation.setUserId(user.getUserId());
        conversation.setTargetId(targetUser.getUserId());
        conversation.setType(createConversationRequest.getType());
        conversation.setTitle(targetUser.getNickname() + " " + targetUser.getLoginName());
        Date date = new Date();
        conversation.setUpdateTime(date);
        conversation.setCreateTime(date);
        conversationMapper.insert(conversation);
        //再给目标用户也创建会话
        Conversation conversationTarget = new Conversation();
        conversationTarget.setConversationId(conversationId);
        conversationTarget.setUserId(targetId);
        conversationTarget.setTargetId(user.getUserId());
        conversationTarget.setType(createConversationRequest.getType());
        conversationTarget.setTitle(user.getNickname() + " " + user.getLoginName());
        conversationTarget.setUpdateTime(date);
        conversationTarget.setCreateTime(date);
        conversationMapper.insert(conversationTarget);
        //返回结果
        CreateConversationResponse createConversationResponse = new CreateConversationResponse();
        createConversationResponse.setConversationId(conversation.getConversationId());
        createConversationResponse.setTargetId(targetId);
        createConversationResponse.setType(createConversationRequest.getType());
        return Result.ok(createConversationResponse);
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
