package com.eg.chatserver.push;

import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.02.01 00:21:07
 */
@Data
public class PushResult {
    private long msg_id;
    private String originalContent;
    private int rateLimitQuota;
    private int rateLimitRemaining;
    private int rateLimitReset;
    private int responseCode;
    private boolean resultOK;
    private long sendno;
    private int statusCode;
}
