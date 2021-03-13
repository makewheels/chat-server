package com.eg.chatserver;

import com.eg.chatserver.utils.JsonToString;

/**
 * @Author makewheels
 * @Time 2021.03.13 11:18:20
 */
@JsonToString
public class TestClass {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
