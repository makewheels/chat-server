package com.eg.chatserver.oss;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.eg.chatserver.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author makewheels
 * @Time 2021.02.01 00:30:25
 */
@Service
@Slf4j
public class OssService {
    @Value("${oss.callbackurl}")
    private String ossCallbackUrl;

    private static final String regionId = "cn-beijing";
    private static final String accessKeyId = "LTAI4GCUwdYeH2YKnSPA8iV6";
    private static final String secret = "2cS8HB6ESVgaLFNWehIfFyVbFWp7kN";
    private static final String bucketName = Constants.ALIYUN.OSS_BUCKET_NAME;
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static final String roleArn = "acs:ram::1618784280874658:role/aliyunosstokengeneratorrole";

    private final DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
    private final IAcsClient client = new DefaultAcsClient(profile);

    /**
     * 获取sts凭证
     *
     * @return
     */
    public AssumeRoleResponse getStsCredential() {
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleArn(roleArn);
        request.setRoleSessionName("external-username");
        request.setDurationSeconds(Constants.ALIYUN.OSS_STS_CREDENTIAL_DURATION);
        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("error on request sts credential");
            log.error("ErrCode: {}", e.getErrCode());
            log.error("ErrMsg: {}", e.getErrMsg());
            log.error("RequestId: {}", e.getRequestId());
            e.printStackTrace();
        }
        return null;
    }

}
