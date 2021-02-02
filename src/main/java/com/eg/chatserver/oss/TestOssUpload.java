package com.eg.chatserver.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.auth.sts.AssumeRoleResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TestOssUpload {
    public static void main(String[] args) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String bucketName = "chat-oss-bucket";

        OssService ossService = new OssService();
        AssumeRoleResponse stsCredential = ossService.getStsCredential();
        AssumeRoleResponse.Credentials credentials = stsCredential.getCredentials();
        String accessKeyId = credentials.getAccessKeyId();
        String accessKeySecret = credentials.getAccessKeySecret();
        String securityToken = credentials.getSecurityToken();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, securityToken);
        String content = "Hello OSS";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                "rubbish/" + System.currentTimeMillis() + "",
                new ByteArrayInputStream(content.getBytes()));

// 上传回调参数。
        Callback callback = new Callback();
        callback.setCallbackUrl("http://c19758058n.imwork.net/chat-server/oss/aliyunCallback");
//        callback.setCallbackHost("oss-cn-beijing.aliyuncs.com");
// 设置发起回调时请求body的值。
        callback.setCallbackBody("{\\\"mimeType\\\":${mimeType},\\\"size\\\":${size}}");
// 设置发起回调请求的Content-Type。
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
// 设置发起回调请求的自定义参数，由Key和Value组成，Key必须以x:开始。
        callback.addCallbackVar("var1", "value1");
        callback.addCallbackVar("var2", "value2");
        putObjectRequest.setCallback(callback);

        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);

// 读取上传回调返回的消息内容。
        byte[] buffer = new byte[1024];
        ResponseMessage responseMessage = putObjectResult.getResponse();
        System.out.println(responseMessage.getStatusCode());
        responseMessage.getContent().read(buffer);
// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        putObjectResult.getResponse().getContent().close();
        System.out.println(new String(buffer));
// 关闭OSSClient。
        ossClient.shutdown();
    }
}
