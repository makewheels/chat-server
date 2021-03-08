package com.eg.chatserver.oss;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.User;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Value("${tencent.cos.domain}")
    private String domain;

    @Value("${tencent.cos.cdnDomain}")
    private String cdnDomain;

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

    //获取音频对象名
    public String getAudioObjectName(User user, String fileId) {
        return "audio/" + user.getUserId() + "/" + fileId;
    }

    //获取图片对象名
    public String getImageObjectName(User user, String fileId) {
        return "image/" + user.getUserId() + "/" + fileId;
    }

    //获取视频对象名
    public String getVideoObjectName(User user, String fileId) {
        return "video/" + user.getUserId() + "/" + fileId;
    }

}
