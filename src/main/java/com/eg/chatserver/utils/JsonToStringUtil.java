package com.eg.chatserver.utils;

import javassist.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author makewheels
 * @Time 2021.03.13 13:22:11
 */
public class JsonToStringUtil {
    public static void modifyToStringMethod(String className) {
        try {
            CtClass ctClass = ClassPool.getDefault().get(className);
            CtMethod[] methods = ctClass.getMethods();
            Arrays.stream(methods).forEach(method -> {
                if (method.getName().equals("toString")) {
                    try {
                        CtMethod delegatorMethod = CtNewMethod.delegator(method, ctClass);
                        delegatorMethod.setBody(
                                "return com.alibaba.fastjson.JSON.toJSONString(this);");
                        ctClass.addMethod(delegatorMethod);
                    } catch (CannotCompileException e) {
                        e.printStackTrace();
                    }
                }
            });
            ctClass.toClass();
        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }

}
