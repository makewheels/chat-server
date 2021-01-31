package com.eg.chatserver.oss;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.stereotype.Service;

import java.security.cert.CRLReason;

/**
 * @Author makewheels
 * @Time 2021.02.01 00:30:25
 */
@Service
public class OssService {
    public static final String REGION = "cn-beijing";
    public static final String STS_API_VERSION = "2015-04-01";

    protected AssumeRoleResponse assumeRole(
            String accessKeyId, String accessKeySecret, String roleArn,
            String roleSessionName, String policy,
            long durationSeconds) throws ClientException {
        // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
        IClientProfile profile = DefaultProfile.getProfile(
                REGION, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 创建一个 AssumeRoleRequest 并设置请求参数
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setVersion(STS_API_VERSION);

        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        request.setPolicy(policy);
        request.setDurationSeconds(durationSeconds);

        // 发起请求，并得到response
        return client.getAcsResponse(request);
    }

    /**
     * Get请求
     * @return
     */
    protected AssumeRoleResponse doGet() throws ClientException {
        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
        String accessKeyId = "LTAI4GAT8ibW1iRDScGyRKTm";
        String accessKeySecret = "muqaYYvst7mYQIEsPIyjK6p4AkGaAT";

        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = "acs:ram::1618784280874658:role/aliyunosstokengeneratorrole";
        long durationSeconds = 900;
        String policy = "{\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Action\": [\n" +
                "        \"oss:PutObject\",\n" +
                "        \"oss:ListParts\",\n" +
                "        \"oss:AbortMultipartUpload\"\n" +
                "      ],\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Resource\": [\"acs:oss:*:*:chat-oss-bucket/chat*\"]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"Version\": \"1\"\n" +
                "}\n";
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "alice-001";

        return assumeRole(accessKeyId, accessKeySecret, roleArn, roleSessionName,
                policy, durationSeconds);

    }

    public static void main(String[] args) throws ClientException {
        AssumeRoleResponse stsResponse = new OssService().doGet();
        AssumeRoleResponse.Credentials credentials = stsResponse.getCredentials();
        System.out.println(credentials.getAccessKeyId());
        System.out.println(credentials.getAccessKeySecret());
        System.out.println(credentials.getExpiration());
        System.out.println(credentials.getSecurityToken());
    }

}
