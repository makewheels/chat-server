package com.eg.chatserver.message.person;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.Conversation;
import com.eg.chatserver.bean.PersonMessage;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.mapper.ConversationMapper;
import com.eg.chatserver.bean.mapper.PersonMessageMapper;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.conversation.ConversationService;
import com.eg.chatserver.jpush.JPushService;
import com.eg.chatserver.message.person.bean.SendMessageRequest;
import com.eg.chatserver.message.person.bean.SendMessageResponse;
import com.eg.chatserver.user.UserAccountService;
import com.eg.chatserver.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:50:48
 */
@Service
@Slf4j
public class PersonMessageService {
    @Resource
    private PersonMessageMapper personMessageMapper;
    @Resource
    private ConversationService conversationService;
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private ConversationMapper conversationMapper;
    @Resource
    private JPushService jPushService;

    /**
     * 生成消息id
     *
     * @return
     */
    private String generatePersonMessageId() {
        return "pmsg" + UuidUtil.getRandomUUid();
    }

    /**
     * 创建一条消息
     *
     * @param sendMessageRequest
     * @param userId
     * @param toUserId
     * @return
     */
    private PersonMessage createPersonMessage(
            SendMessageRequest sendMessageRequest, String userId, String toUserId) {
        PersonMessage personMessage = new PersonMessage();
        String messageId = generatePersonMessageId();
        personMessage.setMessageId(messageId);
        personMessage.setFromUserId(userId);
        personMessage.setToUserId(toUserId);
        personMessage.setConversationId(sendMessageRequest.getConversationId());
        personMessage.setMessageType(sendMessageRequest.getMessageType());
        personMessage.setContent(sendMessageRequest.getContent());
        personMessage.setUrl(sendMessageRequest.getUrl());
        personMessage.setCreateTime(new Date());
        if (sendMessageRequest.getIsForward()) {
            personMessage.setIsForward(true);
            personMessage.setSourceMessageId(sendMessageRequest.getSourceMessageId());
        }
        return personMessage;
    }

    /**
     * 更新会话消息数量
     *
     * @param conversationList
     */
    private void updateConversationListCount(List<Conversation> conversationList) {
        for (Conversation conversation : conversationList) {
            //如果是null，给零
            Integer messageCount = conversation.getMessageCount();
            if (messageCount == null) {
                messageCount = 0;
            }
            Integer unreadMessageCount = conversation.getUnreadMessageCount();
            if (unreadMessageCount == null) {
                unreadMessageCount = 0;
            }
            conversation.setMessageCount(messageCount + 1);
            conversation.setUnreadMessageCount(unreadMessageCount + 1);
            conversationMapper.updateByPrimaryKey(conversation);
        }
    }

    /**
     * 发送对人的消息
     *
     * @param sendMessageRequest
     * @return
     */
    @Transactional
    public Result<SendMessageResponse> sendMessage(User user, SendMessageRequest sendMessageRequest) {
        String conversationId = sendMessageRequest.getConversationId();
        //先根据会话id查找会话
        List<Conversation> conversationList
                = conversationService.findConversationListByConversationId(conversationId);
        //如果没找到
        if (CollectionUtils.isEmpty(conversationList)) {
            log.error("con not find conversation by id: {}", sendMessageRequest.getConversationId());
            return Result.error(ErrorCode.MESSAGE_CANT_FIND_CONVERSATION);
        }
        //因为是给个人发的消息，如果数量不为2，也是错误
        if (conversationList.size() != 2) {
            return Result.error(ErrorCode.MESSAGE_PERSON_CONVERSATION_AMOUNT_NOT_EQUALS_TWO);
        }
        //到这说明已经找到了两个会话
        //找出targetUser
        String userId = user.getUserId();
        String toUserId = null;
        for (Conversation conversation : conversationList) {
            String targetId = conversation.getTargetId();
            if (!userId.equals(targetId)) {
                toUserId = targetId;
            }
        }
        String messageType = sendMessageRequest.getMessageType();
        //创建消息
        PersonMessage personMessage = createPersonMessage(sendMessageRequest, userId, toUserId);
        //保存消息
        personMessageMapper.insert(personMessage);
        updateConversationListCount(conversationList);
        log.info("send person message: {}", JSON.toJSONString(personMessage));
        String messageId = personMessage.getMessageId();
        //TODO 推送
        User targetUser = userAccountService.findUserByUserId(toUserId);
        String jpushRegistrationId = targetUser.getJpushRegistrationId();
//        jPushService.pushByRegistrationId(jpushRegistrationId, personMessage.getMessageId());
        //返回
        SendMessageResponse sendMessageResponse = new SendMessageResponse();
        sendMessageResponse.setMessageId(messageId);
        sendMessageResponse.setConversationId(conversationId);
        sendMessageResponse.setMessageType(messageType);
        return Result.ok(sendMessageResponse);
    }

}
