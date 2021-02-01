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
        String accessKeyId = "STS.NTsGvyy3EC52s2EwKBVVqoxtY";
        String accessKeySecret = "CGZxSHvRs84w2vJtQz95pWH7FSrh8SBKGpbDAuJHTEDF";
        String securityToken = "CAISiwJ1q6Ft5B2yfSjIr5fGDMzNlOxk9PfZcRT0k0sXWtldgL3fuzz2IHlKfXdoAe0fsvQ/nW1Y6fcTlrh+W4NIX0rNaY5t9ZlN9wqkbtI6Ykk6cPpW5qe+EE2/VjTZvqaLEcibIfrZfvCyESOm8gZ43br9cxi7QlWhKufnoJV7b9MRLGLaBHg8c7UwHAZ5r9IAPnb8LOukNgWQ4lDdF011oAFx+wgdgOadupTCs0qB3AGgmrdF+Nivf8ieApMybMslYbCcx/drc6fN6ilU5iVR+b1+5K4+om6Z54/FWwQIv0XWareErYw+NnxwYqkrBqhDt+Pgkv51vOPekYntwgpKJ/tSVynPjnVWGEokgIkagAEUc590Lz5PjZIwrLNH7B6RcL0CrkVG6dUwHNsz+URtpXWnohnKkWUJNoX6vAbk1OZyGXvhvhLKXdQEjqk0rbxIrJRiuZ4wQOzKNTzNjw0a/iBHjuIeiAimXJ8QwCi3V838NEGkDaLHIDxywezqYvYAvLTtZnpYhLB7KmuIR/l8kA==";

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
//        ossClient.deleteObject(bucketName,"1612159906543");
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();

    }
}
