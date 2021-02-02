package com.eg.chatserver.oss;

import lombok.Data;

/**
 * @Author makewheels
 * @Time 2021.02.02 19:02:59
 */
@Data
public class CallbackRequest {
    private String bucket;
    private String object;
    private String etag;
    private long size;
    private String mimeType;
    private int imageInfoHeight;
    private int imageInfoWidth;
    private String imageInfoFormat;
}
