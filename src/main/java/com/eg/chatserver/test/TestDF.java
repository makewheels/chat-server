package com.eg.chatserver.test;

import com.alibaba.fastjson.JSON;

import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class TestDF {

    public static void main(String[] args) throws Exception {
        int bitLength = 512;
        SecureRandom rnd = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitLength, rnd);
        BigInteger g = BigInteger.probablePrime(bitLength, rnd);
        System.out.println(g);
        createSpecificKey(p, g);
    }

    public static void createSpecificKey(BigInteger p, BigInteger g) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DiffieHellman");

        DHParameterSpec param = new DHParameterSpec(p, g);
        kpg.initialize(param);
        KeyPair kp = kpg.generateKeyPair();

        KeyFactory keyFactory = KeyFactory.getInstance("DiffieHellman");

        DHPublicKeySpec kspec = keyFactory.getKeySpec(kp.getPublic(), DHPublicKeySpec.class);
        System.out.println(JSON.toJSONString(kspec));
    }
}
