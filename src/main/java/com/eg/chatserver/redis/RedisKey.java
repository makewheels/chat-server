package com.eg.chatserver.redis;

/**
 * @Author makewheels
 * @Time 2021.01.30 13:00:45
 */
public class RedisKey {
    private static final String ROOT = "chat";
    private static final String USER = ROOT + ":user";

    public static String getLoginTokenKey(String loginToken) {
        return USER + ":loginToken:" + loginToken;
    }
}
