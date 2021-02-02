package com.eg.chatserver.oss;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import lombok.Data;

@Data
public class UploadResponse {
    private AssumeRoleResponse.Credentials credentials;

}
