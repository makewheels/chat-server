package com.eg.chatserver.message;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.jpush.JPushService;
import com.eg.chatserver.jpush.PushResult;
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
     *
     * @param user
     * @param messageId
     */
    public PushResult pushPersonMessage(User user, String messageId) {
        Map<String, String> map = new HashMap<>();
        map.put("version", Constants.PUSH_VERSION);
        map.put("cmd", "pullMessage");
        map.put("type", "person");
        map.put("messageId", messageId);
        return jPushService.pushByRegistrationId(user.getJpushRegistrationId(), JSON.toJSONString(map));
    }
}
