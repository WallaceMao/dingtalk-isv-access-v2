package com.rishiqing.dingtalk.svc.util;

import java.security.MessageDigest;
import java.util.Formatter;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 12:21
 */
public class DingTalkJsApiSingnature {
    public DingTalkJsApiSingnature() {
    }

    public static String getJsApiSingnature(String url, String nonce, Long timeStamp, String jsTicket) throws DingTalkEncryptException {
        String plainTex = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonce + "&timestamp=" + timeStamp + "&url=" + url;
        System.out.println(plainTex);
        String signature = "";

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(plainTex.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            return signature;
        } catch (Exception var7) {
            throw new DingTalkEncryptException(900006);
        }
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] var2 = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
