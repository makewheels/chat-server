package com.eg.chatserver.message;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.jpush.JPushService;
import com.eg.chatserver.jpush.PushResult;
import com.eg.chatserver.utils.Constants;
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

    /**
     * 向客户端推送，告诉他拉取消息
     *
     * @param user
     * @param messageId
     */
    public PushResult pushPersonMessage(User user, String messageId) {
        String content = Constants.PUSH_HEADER + "&cmd=pullMessage" +
                "&type=person" + "&messageId=" + messageId;
        return jPushService.pushByRegistrationId(user.getJpushRegistrationId(), content);
    }
}
