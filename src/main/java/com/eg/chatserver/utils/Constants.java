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

    public static class OSS {
        public static final String IMAGE_PREVIEW_PARAM = "imageMogr2/thumbnail/600x600";

        public static final int STS_CREDENTIAL_DURATION = 1200;
        public static final String DEFAULT_HEAD_IMAGE_URL
                = "https://chat-develop-1253319037.file.myqcloud.com/image/default-head.png";

    }

    public static final String PUSH_VERSION = "1";

    public static final long TIME_10_MINUTES = 10 * 60 * 1000L;
    public static final long TIME_30_MINUTES = 30 * 60 * 1000L;
    public static final long TIME_ONE_HOUR = 60 * 60 * 1000L;

}
