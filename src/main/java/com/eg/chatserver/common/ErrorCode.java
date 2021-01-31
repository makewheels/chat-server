package com.eg.chatserver.common;

public enum ErrorCode {
    SUCCESS(0, "success"),
    FAIL(1, "fail"),

    WRONG_PARAM(2, "传入参数错误"),
    NOT_SUPPORT(3, "不支持此操作"),

    NEED_LOGIN(1000, "请先登录"),
    CHECK_LOGIN_TOKEN_ERROR(1001, "检查loginToken错误"),

    //注册
    REGISTER_LOGIN_NAME_ALREADY_EXISTS(1002, "登录名已存在"),
    //登录
    LOGIN_LOGIN_NAME_PASSWORD_WRONG(1003, "登录名或密码错误"),

    SEARCH_USER_LOGIN_NAME_NOT_EXIST(1004, "搜索登录名不存在"),

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
