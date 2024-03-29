package com.eg.chatserver.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息类型
 *
 * @Author makewheels
 * @Time 2021.01.31 16:48:41
 */
public class MessageType {
    public static final String TEXT = "text";
    public static final String AUDIO = "audio";
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    private static final List<String> messageTypeList = new ArrayList<>();

    static {
        messageTypeList.add(TEXT);
        messageTypeList.add(AUDIO);
        messageTypeList.add(IMAGE);
        messageTypeList.add(VIDEO);
    }

    /**
     * 获取所有消息类型
     *
     * @return
     */
    public static List<String> getAllMessageTypes() {
        return messageTypeList;
    }

    /**
     * 检查种类是否存在
     *
     * @param type
     * @return
     */
    public static boolean checkExist(String type) {
        for (String messageType : messageTypeList) {
            if (messageType.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
