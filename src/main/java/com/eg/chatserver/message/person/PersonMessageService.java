package com.eg.chatserver.message.person;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.*;
import com.eg.chatserver.bean.mapper.ConversationMapper;
import com.eg.chatserver.bean.mapper.FileMapper;
import com.eg.chatserver.bean.mapper.PersonMessageMapper;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.conversation.ConversationService;
import com.eg.chatserver.message.MessageType;
import com.eg.chatserver.message.person.bean.PullMessageResponse;
import com.eg.chatserver.message.person.bean.SendMessageRequest;
import com.eg.chatserver.message.person.bean.SendMessageResponse;
import com.eg.chatserver.oss.OssCredential;
import com.eg.chatserver.oss.OssService;
import com.eg.chatserver.push.MessagePushService;
import com.eg.chatserver.push.PushResult;
import com.eg.chatserver.user.UserAccountService;
import com.eg.chatserver.utils.Constants;
import com.eg.chatserver.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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
    private UserAccountService userAccountService;
    @Resource
    private ConversationService conversationService;
    @Resource
    private MessagePushService messagePushService;
    @Resource
    private OssService ossService;

    @Resource
    private PersonMessageMapper personMessageMapper;
    @Resource
    private ConversationMapper conversationMapper;
    @Resource
    private FileMapper fileMapper;

    /**
     * 生成消息id
     *
     * @return
     */
    private String generatePersonMessageId() {
        return "pmsg" + UuidUtil.getRandomUUid();
    }

    /**
     * 生成文件id
     *
     * @return
     */
    private String generateFileId() {
        return "file" + UuidUtil.getRandomUUid();
    }

    /**
     * 判断消息类型是不是文件
     */
    public boolean isFileTypeMessage(String messageType) {
        return messageType.equals(MessageType.AUDIO)
                || messageType.equals(MessageType.IMAGE)
                || messageType.equals(MessageType.VIDEO);
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
        String messageType = sendMessageRequest.getMessageType();
        personMessage.setMessageType(messageType);
        //如果是文本消息，设置内容
        if (messageType.equals(MessageType.TEXT)) {
            personMessage.setContent(sendMessageRequest.getContent());
        } else if (messageType.equals(MessageType.AUDIO) || messageType.equals(MessageType.IMAGE)
                || messageType.equals(MessageType.VIDEO)) {
            //如果是文件，直接设置上文件id
            personMessage.setFileId(generateFileId());
        }
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
     * 准备发送消息要的东西，返回conversation和targetUser
     *
     * @param conversationId
     * @param userId
     * @return 返回三个参数：
     * 第1个参数，ErrorCode，如果为null，说明正常返回。如果有错误，那就返回错误码
     * 第2个参数，conversationList
     * 第3个参数，targetUser
     */
    private Object[] prepareSendMessage(String conversationId, String userId) {
        //根据会话id查找会话
        List<Conversation> conversationList
                = conversationService.findConversationListByConversationId(conversationId);
        Object[] objects = new Object[3];
        //如果没找到
        if (CollectionUtils.isEmpty(conversationList)) {
            log.error("can not find conversation by id: {}", conversationId);
            objects[0] = ErrorCode.MESSAGE_CANT_FIND_CONVERSATION;
            return objects;
        } else if (conversationList.size() != 2) {
            //因为是给个人发的消息，如果数量不为2，也是错误
            log.error("conversation amount not equals two: {}", conversationId);
            objects[0] = ErrorCode.MESSAGE_PERSON_CONVERSATION_AMOUNT_NOT_EQUALS_TWO;
            return objects;
        }
        objects[1] = conversationList;
        //找出targetUser
        String toUserId = null;
        for (Conversation conversation : conversationList) {
            String targetId = conversation.getTargetId();
            if (!userId.equals(targetId)) {
                toUserId = targetId;
            }
        }
        objects[2] = userAccountService.findUserByUserId(toUserId);
        return objects;
    }

    /**
     * 发送对人的消息
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public Result<SendMessageResponse> sendMessage(User user, SendMessageRequest sendMessageRequest) {
        String conversationId = sendMessageRequest.getConversationId();
        String userId = user.getUserId();
        //准备发消息，获取conversation和targetUser
        Object[] objects = prepareSendMessage(conversationId, userId);
        //先看返回的状态码，如果不为空，那就有错误，返回错误
        ErrorCode errorCode = (ErrorCode) objects[0];
        if (errorCode != null)
            return Result.error(errorCode);
        //能到这说明没有错误了，继续发消息
        List<Conversation> conversationList = (List<Conversation>) objects[1];
        User toUser = (User) objects[2];
        String toUserId = toUser.getUserId();
        //创建消息
        PersonMessage personMessage = createPersonMessage(sendMessageRequest, userId, toUserId);
        //更新conversation消息数量
        updateConversationListCount(conversationList);
        //准备返回结果
        SendMessageResponse sendMessageResponse = getSendMessageResponse(
                user, toUser, sendMessageRequest, personMessage);
        //保存消息
        log.info("save person message: {}", JSON.toJSONString(personMessage));
        personMessageMapper.insert(personMessage);
        return Result.ok(sendMessageResponse);
    }

    /**
     * 发送消息的时候，在保存消息之后
     */
    private SendMessageResponse getSendMessageResponse(
            User fromUser, User toUser, SendMessageRequest sendMessageRequest,
            PersonMessage personMessage) {
        String messageType = sendMessageRequest.getMessageType();
        String messageId = personMessage.getMessageId();
        //如果是文本消息，那不需要后续的上传，直接推送
        //如果不是文本消息，那还不能推送，要等后面文件上传完成之后再推送
        //那我再想个问题哈，如果是视频文件呢？那是不是还要等转码完成
        if (messageType.equals(MessageType.TEXT)) {
            //推送给目标用户，告诉他messageId，让他去拉消息
            //开子线程推送
            new Thread(() ->
                    pushMessageToTargetUser(toUser, messageId)
            ).start();
        }
        SendMessageResponse sendMessageResponse = new SendMessageResponse();
        sendMessageResponse.setMessageId(messageId);
        sendMessageResponse.setConversationId(sendMessageRequest.getConversationId());
        sendMessageResponse.setFromUserId(fromUser.getUserId());
        sendMessageResponse.setToUserId(toUser.getUserId());
        sendMessageResponse.setCreateTime(personMessage.getCreateTime());
        //如果是文件
        if (isFileTypeMessage(messageType)) {
            //处理文件
            handleFile(fromUser, sendMessageRequest, personMessage, sendMessageResponse);
        } else {
            //如果不是文件
            //把上传完成字段置为true
            personMessage.setIsUploadFinish(true);
        }
        return sendMessageResponse;
    }

    /**
     * 根据md5查找文件对象
     *
     * @param md5
     * @return
     */
    private File findFileByMd5(String md5) {
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andMd5EqualTo(md5);
        List<File> files = fileMapper.selectByExample(fileExample);
        if (CollectionUtils.isNotEmpty(files)) {
            return files.get(0);
        }
        return null;
    }

    /**
     * 创建File
     *
     * @return
     */
    private File createFile(SendMessageRequest sendMessageRequest, User fromUser,
                            PersonMessage personMessage) {
        String messageType = sendMessageRequest.getMessageType();
        String fileId = personMessage.getFileId();
        //创建文件
        File file = new File();
        //fileId在之前已经生成了，原因是，message在保存的时候需要关联fileId
        file.setFileId(fileId);
        String originalFilename = sendMessageRequest.getOriginalFilename();
        file.setOriginalName(originalFilename);
        String extension = FilenameUtils.getExtension(originalFilename);
        file.setExtension(extension);
        file.setBucketName(ossService.getBucket());
        file.setMd5(sendMessageRequest.getMd5());
        file.setType(messageType);
        file.setCreateTime(new Date());
        String objectName = null;
        switch (messageType) {
            case MessageType.AUDIO: //音频
                //给阿里云对象名
                objectName = ossService.getAudioObjectName(fromUser, fileId, extension);
                //音频时长
                file.setDuration(sendMessageRequest.getDuration());
                break;
            case MessageType.IMAGE: //图片
                objectName = ossService.getImageObjectName(fromUser, fileId, extension);
                break;
            case MessageType.VIDEO: //视频
                objectName = ossService.getVideoObjectName(fromUser, fileId, extension);
                break;
        }
        //对象名
        file.setObjectName(objectName);
        //文件url
        file.setOssUrl(ossService.getUrl() + "/" + objectName);
        file.setCdnUrl(ossService.getCdnUrl() + "/" + objectName);
        //如果是图片，还要给预览图地址
        if (messageType.equals(MessageType.IMAGE)) {
            file.setImagePreviewUrl(file.getOssUrl() + Constants.OSS.OSS_IMAGE_PREVIEW_PARAM);
        }
        //甚至还需要再给阿里云回调参数
        // 但是这里就先不给了，就让android直接传，messageId就行了
        return file;
    }

    /**
     * 发送消息之，处理文件
     */
    private void handleFile(
            User fromUser, SendMessageRequest sendMessageRequest,
            PersonMessage personMessage, SendMessageResponse sendMessageResponse) {
        //先根据md5找文件，如果已经有了，就不用保存新的文件了
        //其实这种情况，我估计，也就是图片可能遇到
        File file = findFileByMd5(sendMessageRequest.getMd5().toUpperCase());
        //设置上传完成状态
        personMessage.setIsUploadFinish(file != null);
        //如果这个文件已经有了
        if (file != null) {
            //刷新消息里的fileId
            personMessage.setFileId(file.getFileId());
            //告诉客户端不需要上传了
            sendMessageResponse.setIsNeedUpload(false);
        } else {
            //如果是新文件，那就正常创建保存
            file = createFile(sendMessageRequest, fromUser, personMessage);
            //保存文件
            log.info("save file: {}", JSON.toJSONString(file));
            fileMapper.insert(file);
            //告诉客户端需要上传
            sendMessageResponse.setIsNeedUpload(true);
            sendMessageResponse.setRegion(ossService.getRegion());
            sendMessageResponse.setBucket(file.getBucketName());
            sendMessageResponse.setObject(file.getObjectName());
            //获取临时上传凭证
            OssCredential ossCredential = ossService.getCredential(
                    Constants.OSS.OSS_STS_CREDENTIAL_DURATION, file.getObjectName());
            sendMessageResponse.setOssCredential(ossCredential);
        }
    }


    /**
     * 向指定用户推送消息，告诉他拉取一条新消息
     *
     * @param toUser
     * @param messageId
     */
    private void pushMessageToTargetUser(User toUser, String messageId) {
        //如果没有极光注册id
        if (StringUtils.isEmpty(toUser.getJpushRegistrationId())) {
            log.warn("push to person message, target user not login: {}", JSON.toJSONString(toUser));
        } else {
            //正常执行推送
            PushResult pushResult = messagePushService.pushPersonMessage(toUser, messageId);
            if (pushResult.isResultOK()) {
                log.info("push success messageId = {}, pushResult = {}", messageId,
                        JSON.toJSONString(pushResult));
            }
        }
    }

    /**
     * 根据messageId唯一查询一条消息
     *
     * @param messageId
     * @return
     */
    public PersonMessage findByMessageId(String messageId) {
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
    public PersonMessage findByToUserIdAndMessageId(String userId, String messageId) {
        PersonMessageExample personMessageExample = new PersonMessageExample();
        PersonMessageExample.Criteria criteria = personMessageExample.createCriteria();
        criteria.andToUserIdEqualTo(userId);
        criteria.andMessageIdEqualTo(messageId);
        List<PersonMessage> personMessageList
                = personMessageMapper.selectByExampleWithBLOBs(personMessageExample);
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
        PersonMessage personMessage = findByToUserIdAndMessageId(user.getUserId(), messageId);
        //如果没找到这条消息
        if (personMessage == null) {
            return Result.error(ErrorCode.MESSAGE_NOT_EXIST);
        }
        //如果这条消息是文件，并且还没上传好
        if (isFileTypeMessage(personMessage.getMessageType()) && !personMessage.getIsUploadFinish()) {
            return Result.error(ErrorCode.MESSAGE_UPLOAD_NOT_FINISH);
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
        PersonMessage personMessage = findByToUserIdAndMessageId(user.getUserId(), messageId);
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
        PersonMessage personMessage = findByToUserIdAndMessageId(user.getUserId(), messageId);
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
