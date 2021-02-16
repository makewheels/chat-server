package com.eg.chatserver.app;

import lombok.Data;

@Data
public class LatestAppInfoResponse {
    private Integer versionCode;
    private String versionName;
    private String description;
    private Boolean isForceUpdate;
    private String apkDownloadUrl;
}
