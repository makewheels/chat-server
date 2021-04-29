package com.eg.chatserver.oss;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.utils.Constants;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;

/**
 * @Author makewheels
 * @Time 2021.02.01 00:30:25
 */
@Service
@Slf4j
@Data
public class OssService {
    @Value("${tencent.cos.bucket}")
    private String bucket;

    @Value("${tencent.cos.region}")
    private String region;

    @Value("${tencent.cos.secretId}")
    private String secretId;

    @Value("${tencent.cos.secretKey}")
    private String secretKey;

    @Value("${tencent.cos.url}")
    private String url;

    @Value("${tencent.cos.cdnUrl}")
    private String cdnUrl;

    private COSClient cosClient;

    /**
     * 获取对象存储客户端
     *
     * @return
     */
    private COSClient getCOSClient() {
        if (cosClient != null)
            return cosClient;
        COSCredentials credentials = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        return new COSClient(credentials, clientConfig);
    }

    //获取临时凭证
    public OssCredential getCredential(int durationSeconds, String allowPrefix) {
        TreeMap<String, Object> config = new TreeMap<>();
        config.put("SecretId", secretId);
        config.put("SecretKey", secretKey);
        config.put("durationSeconds", durationSeconds);
        config.put("bucket", bucket);
        config.put("region", region);
        if (allowPrefix == null)
            config.put("allowPrefix", "*");
        else
            config.put("allowPrefix", allowPrefix);
        String[] allowActions = {
                // 简单上传
                "name/cos:PutObject",
                // 表单上传、小程序上传
                "name/cos:PostObject",
                // 分片上传
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload"
        };
        config.put("allowActions", allowActions);
        String json = null;
        try {
            JSONObject jsonObject = CosStsClient.getCredential(config);
            if (jsonObject == null)
                return null;
            json = jsonObject.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(json, OssCredential.class);
    }


    public String generatePreSignedUrl(String object) {
        return generatePreSignedUrl(object, Constants.TIME_10_MINUTES);
    }

    /**
     * 获取带签名的url
     *
     * @param object         对象名
     * @param expirationTime 过期时长
     * @return
     */
    public String generatePreSignedUrl(String object, long expirationTime) {
        GeneratePresignedUrlRequest request
                = new GeneratePresignedUrlRequest(bucket, object, HttpMethodName.GET);
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        request.setExpiration(expirationDate);
        String url = getCOSClient().generatePresignedUrl(request).toString();
        if (url.startsWith("http://"))
            url = url.replaceFirst("http://", "https://");
        return url;
    }


    //获取音频对象名
    public String getAudioObjectName(User user, String fileId, String extension) {
        if (extension == null)
            return "audio/" + user.getUserId() + "/" + fileId;
        else
            return "audio/" + user.getUserId() + "/" + fileId + "." + extension;
    }

    //获取图片对象名
    public String getImageObjectName(User user, String fileId, String extension) {
        if (extension == null)
            return "image/" + user.getUserId() + "/" + fileId;
        else
            return "image/" + user.getUserId() + "/" + fileId + "." + extension;
    }

    //获取视频对象名
    public String getVideoObjectName(User user, String fileId, String extension) {
        if (extension == null)
            return "video/" + user.getUserId() + "/" + fileId;
        else
            return "video/" + user.getUserId() + "/" + fileId + "." + extension;
    }

}
