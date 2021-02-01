package com.eg.chatserver.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;

import java.io.File;

public class TestOssUpload {
    public static void main(String[] args) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String bucketName = "chat-oss-bucket";

// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "STS.NTcEguwsYKWRAsubzpaRC2wn6";
        String accessKeySecret = "AYDQrRvkW6odKM6r3ze16Tr1uxRSq3oGdhmGYuprXQpf";
        String securityToken = "CAISiwJ1q6Ft5B2yfSjIr5fWDt3Bmqx4/JW5Q1XEhnolbd1v3bLF1Dz2IHlKfXdoAe0fsvQ/nW1Y6fcTlrh+W4NIX0rNaY5t9ZlN9wqkbtJ9WWU8cPpW5qe+EE2/VjTZvqaLEcibIfrZfvCyESOm8gZ43br9cxi7QlWhKufnoJV7b9MRLGLaBHg8c7UwHAZ5r9IAPnb8LOukNgWQ4lDdF011oAFx+wgdgOadupTCs0qB3AGgmrdF+Nivf8ieApMybMslYbCcx/drc6fN6ilU5iVR+b1+5K4+om6Z54/FWwQIv0XWareErYw+NnxwYqkrBqhDt+Pgkv51vOPekYntwgpKJ/tSVynPjnVWGEokgIkagAFd2V8edJRwqldquktkeOXJVKfalYwwsrL/QaJgOxrRWr8YNyr6a3ci8cfMxzFffsFo4Uhd1FP/HQVn27AwkuZnRyEYfNmKyWtZtgpv1y9e1UFKQ4ab7vMMlTOxrG70L1FB2dcwTcGCLWTcIX56dVZXE5LYJ0cSl80a0kvbV+8UYA==";


// 用户拿到STS临时凭证后，通过其中的安全令牌（SecurityToken）和临时访问密钥（AccessKeyId和AccessKeySecret）生成OSSClient。
// 创建OSSClient实例。注意，这里使用到了上一步生成的临时访问凭证（STS访问凭证）。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
// 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucketName, System.currentTimeMillis() + "",
                new File("D:\\workSpace\\webstorm\\ChromeExtensionBilibiliPlayerListExpand\\manifest.json"));

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(CannedAccessControlList.Private);
        putObjectRequest.setMetadata(metadata);

// 上传文件。
        ossClient.deleteObject(bucketName,"1612158632534");
//        ossClient.putObject(putObjectRequest);

        String policy = "{\"Statement\":[{\"Action\":[\"oss:PutObject\"," +
                "\"oss:ListParts\",\"oss:AbortMultipartUpload\"]," +
                "\"Effect\":\"Allow\",\"Resource\":" +
                "[\"acs:oss:*:*:chat-oss-bucket/chat*\"]}]," +
                "\"Version\":\"1\"}";
        System.out.println(policy);
// 关闭OSSClient。
        ossClient.shutdown();

    }
}
