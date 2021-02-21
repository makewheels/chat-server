package com.eg.chatserver.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
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
        File file = new File("C:\\Users\\thedoflin\\Downloads\\ebook-master\\cbook.pdf");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                "rubbish/" + System.currentTimeMillis() + "."
                        + FilenameUtils.getBaseName(file.getName()), file);

        Callback callback = new Callback();
        callback.setCallbackUrl("http://c19758058n.imwork.net/chat-server/oss/aliyunCallback");
        // 设置发起回调时请求body的值。
        callback.setCallbackBody(
                "{\\\"bucket\\\":${bucket}," +
                        "\\\"object\\\":${object}," +
                        "\\\"etag\\\":${etag}," +
                        "\\\"size\\\":${size}," +
                        "\\\"mimeType\\\":${mimeType}," +
                        "\\\"imageInfo.height\\\":${imageInfo.height}," +
                        "\\\"imageInfo.width\\\":${imageInfo.width}," +
                        "\\\"imageInfo.format\\\":${imageInfo.format}" +
                        ",\\\"aaa\\\":\\\"bbbb\\\"" +
                        "}");
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
        callback.addCallbackVar("var1", "value1");
        callback.addCallbackVar("var2", "value2");
        putObjectRequest.setCallback(callback);

        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);

        byte[] buffer = new byte[1024];
        ResponseMessage responseMessage = putObjectResult.getResponse();
        System.out.println(responseMessage.getStatusCode());
        responseMessage.getContent().read(buffer);
        putObjectResult.getResponse().getContent().close();
        System.out.println(new String(buffer));
        ossClient.shutdown();
    }
}
