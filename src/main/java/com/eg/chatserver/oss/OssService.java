package com.eg.chatserver.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author makewheels
 * @Time 2021.02.01 00:30:25
 */
@Service
@Slf4j
public class OssService {
    @Value("${oss.callbackurl}")
    private String ossCallbackUrl;

    private String regionId = "cn-beijing";
    private String accessKeyId = "LTAI4GCUwdYeH2YKnSPA8iV6";
    private String accessKeySecret = "2cS8HB6ESVgaLFNWehIfFyVbFWp7kN";
    private String bucketName = Constants.ALIYUN.OSS_BUCKET_NAME;
    @Value("${oss.endpoint}")
    private String endpoint;
    private String roleArn = "acs:ram::1618784280874658:role/aliyunosstokengeneratorrole";

    private final DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
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

    /**
     * 阿里云回调验签
     */
    public boolean checkCallback(HttpServletRequest request, String body) {
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKs/JBGzwUB2aVht4crBx3oIPBLNsjGsC0fTXv+nvlmklvkcolvpvXLTjaxUHR3W9LXxQ2EHXAJfCB+6H2YF1k8CAwEAAQ==";
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            String requestURI = URLDecoder.decode(request.getRequestURI(), "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(requestURI);
            String queryString = request.getQueryString();
            if (StringUtils.isNotEmpty(queryString)) {
                stringBuilder.append("?").append(queryString);
            }
            if (StringUtils.isNotEmpty(body)) {
                stringBuilder.append("\n").append(body);
            }
            signature.update(stringBuilder.toString().getBytes());
            byte[] authorization = BinaryUtil.fromBase64String(request.getHeader("Authorization"));
            return signature.verify(authorization);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件元信息
     */
    public ObjectMetadata getMetaData(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata objectMetadata = ossClient.getObjectMetadata(bucketName, objectName);
        ossClient.shutdown();
        return objectMetadata;
    }

    /**
     * 处理阿里云上传回调
     *
     * @param callbackRequest
     */
    public Result<Void> aliyunCallback(CallbackRequest callbackRequest) {
        String object = callbackRequest.getObject();
        ObjectMetadata metaData = getMetaData(object);
        return Result.ok();
    }
}
