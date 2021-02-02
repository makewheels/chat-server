package com.eg.chatserver;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File javaHome = SystemUtils.getUserHome();
        System.out.println(javaHome);
    }
}
