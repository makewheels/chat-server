package com.eg.chatserver.message.person;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.message.MessageType;
import com.eg.chatserver.message.person.bean.MessageIdRequest;
import com.eg.chatserver.message.person.bean.PullMessageResponse;
import com.eg.chatserver.message.person.bean.SendMessageRequest;
import com.eg.chatserver.message.person.bean.SendMessageResponse;
import com.eg.chatserver.user.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:50:11
 */
@RestController
@RequestMapping("/message/person")
@Api(tags = "对人的消息 Controller")
public class PersonMessageController {
    @Resource
    private PersonMessageService personMessageService;
    @Resource
    private UserAccountService userAccountService;

    @PostMapping("/sendMessage")
    @ApiOperation(value = "发送消息")
    public Result<SendMessageResponse> sendMessage(
            @RequestBody SendMessageRequest sendMessageRequest,
            HttpServletRequest request) {
        String conversationId = sendMessageRequest.getConversationId();
        String messageType = sendMessageRequest.getMessageType();
        if (StringUtils.isEmpty(conversationId) || StringUtils.isEmpty(messageType)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        //校验消息类型
        boolean messageTypeExist = MessageType.checkExist(messageType);
        if (!messageTypeExist) {
            return Result.error(ErrorCode.MESSAGE_TYPE_NOT_EXIST);
        }
        User user = userAccountService.getUserByRequest(request);
        return personMessageService.sendMessage(user, sendMessageRequest);
    }

    @PostMapping("/pullByMessageId")
    @ApiOperation(value = "根据消息id拉取一条消息")
    public Result<PullMessageResponse> pullByMessageId(
            HttpServletRequest request, @RequestBody MessageIdRequest messageIdRequest) {
        String messageId = messageIdRequest.getMessageId();
        if (StringUtils.isEmpty(messageId)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        User user = userAccountService.getUserByRequest(request);
        return personMessageService.pullByMessageId(user, messageId);
    }

    @PostMapping("reportArrive")
    @ApiOperation(value = "上报：消息已达")
    public Result<Void> reportArrive(
            HttpServletRequest request, @RequestBody MessageIdRequest messageIdRequest) {
        String messageId = messageIdRequest.getMessageId();
        if (StringUtils.isEmpty(messageId)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        User user = userAccountService.getUserByRequest(request);
        return personMessageService.reportArrive(user, messageId);
    }

    @PostMapping("reportRead")
    @ApiOperation(value = "上报：消息已读")
    public Result<Void> reportRead(
            HttpServletRequest request, @RequestBody MessageIdRequest messageIdRequest) {
        String messageId = messageIdRequest.getMessageId();
        if (StringUtils.isEmpty(messageId)) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        User user = userAccountService.getUserByRequest(request);
        return personMessageService.reportRead(user, messageId);
    }
}
