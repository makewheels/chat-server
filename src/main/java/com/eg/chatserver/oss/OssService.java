package com.eg.chatserver.oss;

import com.eg.chatserver.bean.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author makewheels
 * @Time 2021.02.01 00:30:25
 */
@Service
@Slf4j
@Getter
public class OssService {
    @Value("${qcloud.cos.bucketName}")
    private String bucketName;
    @Value("${qcloud.cos.secretId}")
    private String secretId;
    @Value("${qcloud.cos.secretKey}")
    private String secretKey;
    @Value("${qcloud.cos.domain}")
    private String domain;
    @Value("${qcloud.cos.cdnDomain}")
    private String cdnDomain;

    /**
     * 获取音频对象名
     */
    public String getAudioObjectName(User user, String fileId) {
        return "audio/" + user.getUserId() + "/" + fileId;
    }

    /**
     * 获取图片对象名
     */
    public String getImageObjectName(User user, String fileId) {
        return "image/" + user.getUserId() + "/" + fileId;
    }

    /**
     * 获取视频对象名
     */
    public String getVideoObjectName(User user, String fileId) {
        return "video/" + user.getUserId() + "/" + fileId;
    }

}
