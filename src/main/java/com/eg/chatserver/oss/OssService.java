package com.eg.chatserver.oss;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.eg.chatserver.utils.Constants;
import org.springframework.stereotype.Service;

/**
 * @Author makewheels
 * @Time 2021.02.01 00:30:25
 */
@Service
public class OssService {
    private static final String regionId = "cn-beijing";
    private static final String accessKeyId = "LTAI4GCUwdYeH2YKnSPA8iV6";
    private static final String secret = "2cS8HB6ESVgaLFNWehIfFyVbFWp7kN";
    private static final String bucketName = "chat-oss-bucket";
    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

    private final DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
    private final IAcsClient client = new DefaultAcsClient(profile);

    public AssumeRoleResponse getStsToken() {
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleArn("acs:ram::1618784280874658:role/aliyunosstokengeneratorrole");
        request.setRoleSessionName("external-username");
        request.setDurationSeconds(Constants.ALIYUN.OSS_STS_CREDENTIALS_DURATION);
        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return null;

    }

    public static void main(String[] args) {
        OssService ossService = new OssService();
        AssumeRoleResponse stsToken = ossService.getStsToken();
        System.out.println(JSON.toJSONString(stsToken));

    }

}
