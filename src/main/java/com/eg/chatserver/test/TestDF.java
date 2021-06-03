package com.eg.chatserver.test;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import org.apache.logging.log4j.util.Base64Util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TestDF {
    public static BigInteger get() {
        return new BigInteger(RandomUtil.randomInt(2, 16) + "");
    }

    public static int getRandomInt() {
        int result = RandomUtil.randomInt(256);
        while (result <= 0) {
            result = RandomUtil.randomInt(256);
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

        BigInteger gab = ga.pow(b);

        System.out.println("gab = " + gab);

        System.out.println();
        System.out.println("ga.toString(36) = " + ga.toString(36));
        System.out.println(Base64.encode(ga.toString(36)));
        System.out.println(Base64.encode(gb.toString(36)));
        System.out.println(Base64.encode(gab.toString(36)));
    }
}
