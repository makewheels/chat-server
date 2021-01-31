package com.eg.chatserver.common;

public enum ErrorCode {
    SUCCESS(0, "success"),
    FAIL(1, "fail"),

    WRONG_PARAM(2, "传入参数错误"),
    NOT_SUPPORT(3, "不支持此操作"),

    NEED_LOGIN(1000, "请先登录"),
    CHECK_LOGIN_TOKEN_ERROR(1001, "检查loginToken错误"),

    REGISTER_LOGIN_NAME_ALREADY_EXISTS(1002, "登录名已存在"),
    LOGIN_LOGIN_NAME_PASSWORD_WRONG(1003, "登录名或密码错误"),
    SEARCH_USER_LOGIN_NAME_NOT_EXIST(1004, "搜索登录名不存在"),
    LOGIN_JPUSH_REGISTRATION_ID_IS_EMPTY(1005, "极光推送id为空，请稍后再试"),

    CONVERSATION_CREATE_TARGET_USER_NOT_EXIST(2000, "创建会话错误：目标用户不存在"),
    CONVERSATION_CREATE_WITH_MY_SELF(2001, "和自己聊有意思么？"),
    CONVERSATION_CREATE_REPEAT(2002, "请不要重复创建会话"),

    MESSAGE_TYPE_NOT_EXIST(3000, "消息类型不存在"),
    MESSAGE_CANT_FIND_CONVERSATION(3001, "找不到会话"),
    MESSAGE_PERSON_CONVERSATION_AMOUNT_NOT_EQUALS_TWO(3002, "给人发的消息，找到会话数量不等于2"),
    MESSAGE_NOT_EXIST(3003, "消息不存在"),
    MESSAGE_CANT_GET_OTHER_USER_MESSAGE(3004, "不能获取他人信息"),

    RUBBISH(1415926535, "我是垃圾，请忽略我");

    private int code;
    private String value;

    ErrorCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
