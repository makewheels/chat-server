package com.eg.chatserver.utils;

import java.util.UUID;

public class UuidUtil {
    public static String getRandomUUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
