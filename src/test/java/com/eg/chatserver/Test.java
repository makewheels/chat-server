package com.eg.chatserver;

import javassist.*;

import java.util.Arrays;

public class Test {

    private void modifyToStringMethod(String className) {
        try {
            CtClass ctClass = ClassPool.getDefault().get(className);
            CtMethod[] methods = ctClass.getMethods();
            Arrays.stream(methods).forEach(method -> {
                if (method.getName().equals("toString")) {
                    try {
                        CtMethod delegatorMethod = CtNewMethod.delegator(method, ctClass);
                        delegatorMethod.setBody("return com.alibaba.fastjson.JSON.toJSONString(this);");
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

    public static void main(String[] args) {

    }
}
