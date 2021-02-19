package com.eg.chatserver.rtc;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;

/*
 * Function: 用于生成测试用的 UserSig，UserSig 是腾讯云为其云服务设计的一种安全保护签名。
 *           其计算方法是对 SDKAppID、UserID 和 EXPIRETIME 进行加密，加密算法为 HMAC-SHA256。
 *
 * Attention: 请不要将如下代码发布到您的线上正式版本的 App 中，原因如下：
 *
 *            本文件中的代码虽然能够正确计算出 UserSig，但仅适合快速调通 SDK 的基本功能，不适合线上产品，
 *            这是因为客户端代码中的 SECRETKEY 很容易被反编译逆向破解，尤其是 Web 端的代码被破解的难度几乎为零。
 *            一旦您的密钥泄露，攻击者就可以计算出正确的 UserSig 来盗用您的腾讯云流量。
 *
 *            正确的做法是将 UserSig 的计算代码和加密密钥放在您的业务服务器上，然后由 App 按需向您的服务器获取实时算出的 UserSig。
 *            由于破解服务器的成本要高于破解客户端 App，所以服务器计算的方案能够更好地保护您的加密密钥。
 *
 * Reference：https://cloud.tencent.com/document/product/269/32688#Server
 */
public class GenerateTestUserSig {
    private static final int SDKAPPID = 1400486439;
    private static final int EXPIRETIME = 604800;
    private static final String SECRETKEY = "1d51b8f3d194bc2b891be9eb776ff6e09da62307af12f57541979521f51862a2";

    /**
     * 计算 UserSig 签名
     * 函数内部使用 HMAC-SHA256 非对称加密算法，对 SDKAPPID、userId 和 EXPIRETIME 进行加密。
     * <p>
     * 文档：https://cloud.tencent.com/document/product/269/32688#Server
     */
    public static String genTestUserSig(String userId) {
        return genTLSSignature(userId);
    }

    /**
     * 生成 tls 票据
     *
     * @param userId 用户 id
     * @return 如果出错，会返回为空，或者有异常打印，成功返回有效的票据
     */
    private static String genTLSSignature(String userId) {
        if (StringUtils.isEmpty(GenerateTestUserSig.SECRETKEY)) {
            return "";
        }
        long currTime = System.currentTimeMillis() / 1000;
        JSONObject sigDoc = new JSONObject();
        try {
            sigDoc.put("TLS.ver", "2.0");
            sigDoc.put("TLS.identifier", userId);
            sigDoc.put("TLS.sdkappid", (long) GenerateTestUserSig.SDKAPPID);
            sigDoc.put("TLS.expire", (long) GenerateTestUserSig.EXPIRETIME);
            sigDoc.put("TLS.time", currTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String base64UserBuf = null;
        String sig = hmacsha256(userId, currTime, base64UserBuf);
        if (sig.length() == 0) {
            return "";
        }
        try {
            sigDoc.put("TLS.sig", sig);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Deflater compressor = new Deflater();
        compressor.setInput(sigDoc.toString().getBytes(StandardCharsets.UTF_8));
        compressor.finish();
        byte[] compressedBytes = new byte[2048];
        int compressedBytesLength = compressor.deflate(compressedBytes);
        compressor.end();
        return new String(base64EncodeUrl(Arrays.copyOfRange(compressedBytes, 0, compressedBytesLength)));
    }


    private static String hmacsha256(String userId, long currTime, String base64Userbuf) {
        String contentToBeSigned = "TLS.identifier:" + userId + "\n"
                + "TLS.sdkappid:" + (long) GenerateTestUserSig.SDKAPPID + "\n"
                + "TLS.time:" + currTime + "\n"
                + "TLS.expire:" + (long) GenerateTestUserSig.EXPIRETIME + "\n";
        if (null != base64Userbuf) {
            contentToBeSigned += "TLS.userbuf:" + base64Userbuf + "\n";
        }
        try {
            byte[] byteKey = GenerateTestUserSig.SECRETKEY.getBytes(StandardCharsets.UTF_8);
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA256");
            hmac.init(keySpec);
            byte[] byteSig = hmac.doFinal(contentToBeSigned.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(byteSig));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static byte[] base64EncodeUrl(byte[] input) {
        byte[] base64 = new String(Base64.getEncoder().encode(input)).getBytes();
        for (int i = 0; i < base64.length; ++i)
            switch (base64[i]) {
                case '+':
                    base64[i] = '*';
                    break;
                case '/':
                    base64[i] = '-';
                    break;
                case '=':
                    base64[i] = '_';
                    break;
                default:
                    break;
            }
        return base64;
    }

}
