package com.eg.chatserver.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 短信服务
 *
 * @Author makewheels
 * @Time 2021.02.21 22:43:24
 */
@Service
@Slf4j
public class SmsService {
    /**
     * 发送短信验证码
     */
    public void sendVerificationCode(String phone, String verificationCode) {
        log.info("sendVerificationCode phone: {}, verificationCode: {}", phone, verificationCode);
    }
}
