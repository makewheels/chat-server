package com.eg.chatserver.message.person;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.Conversation;
import com.eg.chatserver.bean.PersonMessage;
import com.eg.chatserver.bean.PersonMessageExample;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.mapper.ConversationMapper;
import com.eg.chatserver.bean.mapper.PersonMessageMapper;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.conversation.ConversationService;
import com.eg.chatserver.jpush.PushResult;
import com.eg.chatserver.message.MessagePushService;
import com.eg.chatserver.message.MessageType;
import com.eg.chatserver.message.person.bean.PullMessageResponse;
import com.eg.chatserver.message.person.bean.SendMessageRequest;
import com.eg.chatserver.message.person.bean.SendMessageResponse;
import com.eg.chatserver.user.UserAccountService;
import com.eg.chatserver.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
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
    private MessagePushService messagePushService;

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
        personMessage.setCreateTime(new Date());
        return personMessage;
    }

    /**
     * 更新会话消息数量
     *
     * @param conversationList
     */
    private void updateConversationListCount(List<Conversation> conversationList) {
        //更新两个conversation相同的时间
        Date updateTime = new Date();
        for (Conversation conversation : conversationList) {
            //消息总量和未读数量，如果是null，给零
            Integer messageCount = conversation.getMessageCount();
            Integer unreadMessageCount = conversation.getUnreadMessageCount();
            //更新对象
            Conversation conversationUpdate = new Conversation();
            conversationUpdate.setId(conversation.getId());
            conversationUpdate.setMessageCount(messageCount + 1);
            conversationUpdate.setUnreadMessageCount(unreadMessageCount + 1);
            conversationUpdate.setUpdateTime(updateTime);
            conversationMapper.updateByPrimaryKeySelective(conversationUpdate);
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
        String messageType = sendMessageRequest.getMessageType();
        if (messageType.equals(MessageType.TEXT)) {
            return sendTextMessage(user, sendMessageRequest);
        } else if (messageType.equals(MessageType.IMAGE)) {
            return null;
        } else if (messageType.equals(MessageType.AUDIO)) {
            return null;
        } else if (messageType.equals(MessageType.VIDEO)) {
            return null;
        }
        return null;
    }

    /**
     * 发送文本消息
     *
     * @param user
     * @param sendMessageRequest
     * @return
     */
    private Result<SendMessageResponse> sendTextMessage(User user, SendMessageRequest sendMessageRequest) {
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
        //创建消息
        PersonMessage personMessage = createPersonMessage(sendMessageRequest, userId, toUserId);
        //保存消息
        personMessageMapper.insert(personMessage);
        //更新conversation消息数量
        updateConversationListCount(conversationList);
        log.info("send person message: {}", JSON.toJSONString(personMessage));
        String messageId = personMessage.getMessageId();
        //推送
        User toUser = userAccountService.findUserByUserId(toUserId);
        PushResult pushResult = messagePushService.pushPersonMessage(toUser, messageId);
        if (pushResult.isResultOK()) {
            log.info("push success messageId = {}, pushResult = {}", messageId, JSON.toJSONString(pushResult));
        }
        //返回
        SendMessageResponse sendMessageResponse = new SendMessageResponse();
        sendMessageResponse.setMessageId(messageId);
        sendMessageResponse.setConversationId(conversationId);
        sendMessageResponse.setFromUserId(userId);
        sendMessageResponse.setToUserId(toUserId);
        sendMessageResponse.setCreateTime(personMessage.getCreateTime());
        return Result.ok(sendMessageResponse);
    }

    /**
     * 根据messageId唯一查询一条消息
     *
     * @param messageId
     * @return
     */
    public PersonMessage findSingleByMessageId(String messageId) {
        PersonMessageExample personMessageExample = new PersonMessageExample();
        PersonMessageExample.Criteria criteria = personMessageExample.createCriteria();
        criteria.andMessageIdEqualTo(messageId);
        List<PersonMessage> personMessageList = personMessageMapper.selectByExample(personMessageExample);
        if (CollectionUtils.isEmpty(personMessageList)) {
            return null;
        } else {
            return personMessageList.get(0);
        }
    }

    /**
     * 根据用户和消息id查找一条消息
     */
    public PersonMessage findSingleByToUserIdAndMessageId(String userId, String messageId) {
        PersonMessageExample personMessageExample = new PersonMessageExample();
        PersonMessageExample.Criteria criteria = personMessageExample.createCriteria();
        criteria.andToUserIdEqualTo(userId);
        criteria.andMessageIdEqualTo(messageId);
        List<PersonMessage> personMessageList = personMessageMapper.selectByExampleWithBLOBs(personMessageExample);
        if (CollectionUtils.isEmpty(personMessageList)) {
            return null;
        } else {
            return personMessageList.get(0);
        }
    }

    /**
     * 根据消息id查询一条消息
     */
    public Result<PullMessageResponse> pullByMessageId(User user, String messageId) {
        PersonMessage personMessage = findSingleByToUserIdAndMessageId(user.getUserId(), messageId);
        if (personMessage == null) {
            return Result.error(ErrorCode.MESSAGE_NOT_EXIST);
        }
        PullMessageResponse pullMessageResponse = new PullMessageResponse();
        BeanUtils.copyProperties(personMessage, pullMessageResponse);
        return Result.ok(pullMessageResponse);
    }

    /**
     * 上报：消息已达
     *
     * @param user
     * @param messageId
     * @return
     */
    public Result<Void> reportArrive(User user, String messageId) {
        PersonMessage personMessage = findSingleByToUserIdAndMessageId(user.getUserId(), messageId);
        if (personMessage == null) {
            return Result.error(ErrorCode.MESSAGE_NOT_EXIST);
        }
        //重复上报
        if ((personMessage.getIsArrive() != null && personMessage.getIsArrive())
                || personMessage.getArriveTime() != null) {
            return Result.error(ErrorCode.MESSAGE_REPEAT_ARRIVE_REPEAT);
        }
        //更新数据库
        PersonMessage personMessageUpdate = new PersonMessage();
        personMessageUpdate.setId(personMessage.getId());
        personMessageUpdate.setIsArrive(true);
        personMessageUpdate.setArriveTime(new Date());
        personMessageMapper.updateByPrimaryKeySelective(personMessageUpdate);
        return Result.ok();
    }

    /**
     * 上报：消息已读
     *
     * @param user
     * @param messageId
     * @return
     */
    public Result<Void> reportRead(User user, String messageId) {
        PersonMessage personMessage = findSingleByToUserIdAndMessageId(user.getUserId(), messageId);
        if (personMessage == null) {
            return Result.error(ErrorCode.MESSAGE_NOT_EXIST);
        }
        //重复上报
        if ((personMessage.getIsRead() != null && personMessage.getIsRead())
                || personMessage.getReadTime() != null) {
            return Result.error(ErrorCode.MESSAGE_REPEAT_ARRIVE_REPEAT);
        }
        //更新数据库
        PersonMessage personMessageUpdate = new PersonMessage();
        personMessageUpdate.setId(personMessage.getId());
        personMessageUpdate.setIsRead(true);
        personMessageUpdate.setReadTime(new Date());
        personMessageMapper.updateByPrimaryKeySelective(personMessageUpdate);
        return Result.ok();
    }
}
