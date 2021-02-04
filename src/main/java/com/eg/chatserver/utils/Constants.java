package com.eg.chatserver.utils;

/**
 * @Author makewheels
 * @Time 2021.01.30 11:12:35
 */
public class Constants {
    public static class KEY {
        public static final String LOGIN_TOKEN = "loginToken";

    }

    public static class CONVERSATION {
        public static final String TYPE_PERSON = "person";
        public static final String TYPE_GROUP = "group";
    }

    public static class ALIYUN {
        public static final String OSS_BUCKET_NAME = "chat-oss-bucket";

        public static final String OSS_PREFIX = "https://chat-oss-bucket.oss-cn-beijing.aliyuncs.com/";
        public static final String OSS_PREFIX_MY_DOMAIN = "https://oss-chat.java8.icu/";
        public static final String OSS_IMAGE_PROCESS_600 = "?x-oss-process=image/resize,w_600";

        public static final long OSS_STS_CREDENTIAL_DURATION = 900L;
        public static final String DEFAULT_HEAD_IMAGE_URL = OSS_PREFIX_MY_DOMAIN + "image/default-head.png";

    }

    public static final String PUSH_HEADER = "version=1";


}
