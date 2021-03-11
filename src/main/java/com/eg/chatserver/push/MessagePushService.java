package com.eg.chatserver.push;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.PersonMessage;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.utils.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
     */
    public PushResult pushPersonMessage(User user, PersonMessage personMessage) {
        Map<String, String> map = new HashMap<>();
        map.put("version", Constants.PUSH_VERSION);
        map.put("cmd", "pullMessage");
        //是人的消息，还是群消息
        map.put("type", Constants.CONVERSATION.TYPE_PERSON);
        //消息类型
        map.put("messageType", personMessage.getMessageType());
        map.put("messageId", personMessage.getMessageId());
        String registrationId = user.getJpushRegistrationId();
        return jPushService.pushByRegistrationId(registrationId, JSON.toJSONString(map));
    }
}
