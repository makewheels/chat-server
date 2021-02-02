package com.eg.chatserver.conversation;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.conversation.bean.ConversationResponse;
import com.eg.chatserver.conversation.bean.CreateConversationRequest;
import com.eg.chatserver.conversation.bean.CreateConversationResponse;
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
import java.util.List;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:48:35
 */
@RestController
@RequestMapping("conversation")
@Api(tags = "会话 Controller")
public class ConversationController {
    @Resource
    private ConversationService conversationService;

    @Resource
    private UserAccountService userAccountService;

    @ApiOperation(value = "创建会话")
    @PostMapping("/create")
    public Result<CreateConversationResponse> createConversation(
            @RequestBody CreateConversationRequest createConversationRequest,
            HttpServletRequest request) {
        if (StringUtils.isEmpty(createConversationRequest.getTargetId()) ||
                StringUtils.isEmpty(createConversationRequest.getType())) {
            return Result.error(ErrorCode.WRONG_PARAM);
        }
        User user = userAccountService.getUserByRequest(request);
        return conversationService.create(user, createConversationRequest);
    }

    @ApiOperation(value = "拉取所有会话")
    @PostMapping("/pullAllConversations")
    public Result<List<ConversationResponse>> pullAllConversations(HttpServletRequest request) {
        User user = userAccountService.getUserByRequest(request);
        return conversationService.getAllConversations(user);
    }

}
