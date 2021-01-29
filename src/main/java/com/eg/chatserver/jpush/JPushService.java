package com.eg.chatserver.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author makewheels
 * @Time 2021.01.29 23:53:31
 */
@Service
@Slf4j
public class JPushService {

    protected static final String APP_KEY = "bcec2d094f36af5587048c02";
    protected static final String MASTER_SECRET = "e96cc756b00214485697d048";
    private static final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY,
            null, ClientConfig.getInstance());

    /**
     * 根据注册id推送消息
     *
     * @param registrationId
     * @param message
     * @return
     */
    public String pushByRegistrationId(String registrationId, String message) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationId))
                .setMessage(Message.content(message))
                .build();
        PushResult result = null;
        try {
            result = jpushClient.sendPush(payload);
            jpushClient.close();
            log.info("Got result - " + result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
        return JSON.toJSONString(result);
    }
}
