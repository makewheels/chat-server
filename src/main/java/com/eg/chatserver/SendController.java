package com.eg.chatserver;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author makewheels
 * @Time 2021.01.28 22:54:43
 */
@RestController
public class SendController {
    protected static final Logger LOG = LoggerFactory.getLogger(SendController.class);

    protected static final String APP_KEY = "bcec2d094f36af5587048c02";
    protected static final String MASTER_SECRET = "e96cc756b00214485697d048";
    private static final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY,
            null, ClientConfig.getInstance());

    @RequestMapping("sendMessage")
    public String sendMessage(@RequestParam String message) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId("100d855909e0fa5a708"))
//                .setAudience(Audience.registrationId("1a0018970a057b1d297"))
                .setMessage(Message.content(message + " " + System.currentTimeMillis()))
                .build();
        PushResult result = null;
        try {
            result = jpushClient.sendPush(payload);
            jpushClient.close();
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOG.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
        return JSON.toJSONString(result);
    }
}
