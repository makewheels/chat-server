package com.eg.chatserver.conversation;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.Conversation;
import com.eg.chatserver.bean.ConversationExample;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.mapper.ConversationMapper;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.conversation.bean.CreateConversationRequest;
import com.eg.chatserver.conversation.bean.CreateConversationResponse;
import com.eg.chatserver.user.UserAccountService;
import com.eg.chatserver.utils.Constants;
import com.eg.chatserver.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:48:54
 */
@Service
@Slf4j
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
     * 根据userId和targetId查会话
     *
     * @param userId
     * @param targetId
     * @return
     */
    private Conversation findConversationByUserIdAndTargetId(String userId, String targetId) {
        ConversationExample conversationExample = new ConversationExample();
        ConversationExample.Criteria criteria = conversationExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andTargetIdEqualTo(targetId);
        List<Conversation> conversationList = conversationMapper.selectByExample(conversationExample);
        if (CollectionUtils.isEmpty(conversationList)) {
            return null;
        } else {
            if (conversationList.size() >= 2) {
                log.error("find conversation size more than two! {}", JSON.toJSONString(conversationList));
            }
            return conversationList.get(0);
        }
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
        if (type.equals(Constants.CONVERSATION.TYPE_PERSON)) {
            return createPersonConversation(user, createConversationRequest);
        } else if (type.equals(Constants.CONVERSATION.TYPE_GROUP)) {
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
        //如果和自己创建会话
        if (user.getUserId().equals(targetId)) {
            log.error("create conversation with my self, user: {}", JSON.toJSONString(user));
            return Result.error(ErrorCode.CONVERSATION_CREATE_WITH_MY_SELF);
        }
        String type = createConversationRequest.getType();
        //创建会话之前，先看该会话是否已经存在
        Conversation conversationByUserIdAndTargetId = findConversationByUserIdAndTargetId(user.getUserId(), targetId);
        //如果已存在，返回错误信息
        if (conversationByUserIdAndTargetId != null) {
            CreateConversationResponse createConversationResponse = new CreateConversationResponse();
            createConversationResponse.setConversationId(conversationByUserIdAndTargetId.getConversationId());
            createConversationResponse.setType(type);
            createConversationResponse.setTargetId(targetId);
            log.error("create conversation already exist: {}", JSON.toJSONString(conversationByUserIdAndTargetId));
            return Result.error(ErrorCode.CONVERSATION_CREATE_REPEAT, createConversationResponse);
        }
        //开始创建会话
        Conversation conversation = new Conversation();
        String conversationId = generateConversationId();
        conversation.setConversationId(conversationId);
        conversation.setUserId(user.getUserId());
        conversation.setTargetId(targetUser.getUserId());
        conversation.setType(type);
        conversation.setTitle(targetUser.getNickname() + " " + targetUser.getLoginName());
        Date createTime = new Date();
        conversation.setUpdateTime(createTime);
        conversation.setCreateTime(createTime);
        conversationMapper.insert(conversation);
        //再给目标用户也创建会话
        Conversation conversationTarget = new Conversation();
        conversationTarget.setConversationId(conversationId);
        conversationTarget.setUserId(targetId);
        conversationTarget.setTargetId(user.getUserId());
        conversationTarget.setType(type);
        conversationTarget.setTitle(user.getNickname() + " " + user.getLoginName());
        conversationTarget.setUpdateTime(createTime);
        conversationTarget.setCreateTime(createTime);
        conversationMapper.insert(conversationTarget);
        log.info("create conversation user: {}", JSON.toJSONString(conversation));
        log.info("create conversation target: {}", JSON.toJSONString(conversationTarget));
        //返回结果
        CreateConversationResponse createConversationResponse = new CreateConversationResponse();
        createConversationResponse.setConversationId(conversation.getConversationId());
        createConversationResponse.setTargetId(targetId);
        createConversationResponse.setType(createConversationRequest.getType());
        return Result.ok(createConversationResponse);
        //TODO 这里有个问题，创建会话成功后，是不是应该推送通知目标用户？
        //TODO 放入redis?
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

    /**
     * 根据会话id查找会话
     *
     * @param conversationId
     * @return
     */
    public List<Conversation> findConversationListByConversationId(String conversationId) {
        ConversationExample conversationExample = new ConversationExample();
        ConversationExample.Criteria criteria = conversationExample.createCriteria();
        criteria.andConversationIdEqualTo(conversationId);
        return conversationMapper.selectByExample(conversationExample);
    }

}
