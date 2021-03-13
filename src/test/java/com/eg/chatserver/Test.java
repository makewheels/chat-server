package com.eg.chatserver;

import com.eg.chatserver.bean.PersonMessage;
import com.eg.chatserver.utils.ClassUtil;
import com.eg.chatserver.utils.JsonToString;
import com.eg.chatserver.utils.JsonToStringUtil;

import java.util.Arrays;
import java.util.Set;

/**
 * @Author makewheels
 * @Time 2021.03.13 17:17:25
 */
public class Test {
    public static void main(String[] args) {
        String[] beans = {
                "com.eg.chatserver.bean.Conversation",
                "com.eg.chatserver.bean.File",
                "com.eg.chatserver.bean.PersonMessage",
                "com.eg.chatserver.bean.User"
        };
        Arrays.stream(beans).forEach(JsonToStringUtil::modifyToStringMethod);

        Set<Class<?>> classSet = ClassUtil.getClasses("com.eg.chatserver.bean", true);
        classSet.forEach(System.out::println);
        classSet.stream()
                .filter(clazz -> clazz.isAnnotationPresent(JsonToString.class))
                .forEach(clazz -> JsonToStringUtil.modifyToStringMethod(clazz.getName()));

        PersonMessage personMessage = new PersonMessage();
        personMessage.setContent("fweafwe");
        System.out.println(personMessage);
    }
}
