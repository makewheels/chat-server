package com.eg.chatserver.message;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.jpush.JPushService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author makewheels
 * @Time 2021.01.31 19:50:58
 */
@Service
public class MessagePushService {
    @Resource
    private JPushService jPushService;

    public void pushPersonMessage(User user, String messageId) {
        String content = "cmd=pullMessage&type=person&messageId=" + messageId;
        jPushService.pushByRegistrationId(user.getJpushRegistrationId(), content);
    }
}
