package com.eg.chatserver.test;

import cn.hutool.core.util.RandomUtil;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TestDF {
    public static BigInteger get() {
        return new BigInteger(RandomUtil.randomInt(16) + "");
    }

    public static int getRandomInt() {
        int result = RandomUtil.randomInt(Integer.MAX_VALUE / 40960);
        while (result <= 0) {
            result = RandomUtil.randomInt(Integer.MAX_VALUE / 40960);
        }
        return result;
    }

    public static void main(String[] argv) {
        BigInteger g = get();
        System.out.println("服务器分配g = " + g);

        int a = getRandomInt();
        System.out.println("Alice本地生成 a = " + a);
        int b = getRandomInt();
        System.out.println("Bob  本地生成 b = " + b);

        BigInteger ga = g.pow(a);
        BigInteger gb = g.pow(b);

        System.out.println("ga = " + ga);
        System.out.println("gb = " + gb);

        System.out.println("ga.pow(b) = " + ga.pow(b));
    }
}
