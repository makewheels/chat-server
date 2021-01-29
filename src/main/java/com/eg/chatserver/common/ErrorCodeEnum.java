package com.eg.chatserver.common;

public enum ErrorCodeEnum {
    SUCCESS(1, "成功"),
    FAIL(0, "错误");

    private int code;
    private String value;

    ErrorCodeEnum(int code, String value) {
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
