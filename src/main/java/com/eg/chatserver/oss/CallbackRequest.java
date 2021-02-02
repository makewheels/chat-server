package com.eg.chatserver.oss;

import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.02.02 19:02:59
 */
@Data
public class CallbackRequest {
    private String mimeType;
    private long size;
    private String var1;
}
