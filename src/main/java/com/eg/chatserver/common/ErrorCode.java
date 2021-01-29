package com.eg.chatserver.common;

public enum ErrorCode {
    SUCCESS(0, "success"),
    FAIL(1, "fail"),

    WRONG_PARAM(2, "传入参数错误"),
    REGISTER_LOGIN_NAME_ALREADY_EXISTS(1000, "登录名已存在");


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
