package com.eg.chatserver.oss;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
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

    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleArn("acs:ram::1618784280874658:role/aliyunosstokengeneratorrole");
        request.setRoleSessionName("external-username");
        request.setDurationSeconds(900L);
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

}
