package com.rishiqing.dingtalk.biz.util;

import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-05-10 22:14
 */
public class CryptoUtil {
    private String aesKey;
    private String aesInitVector;

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getAesInitVector() {
        return aesInitVector;
    }

    public void setAesInitVector(String aesInitVector) {
        this.aesInitVector = aesInitVector;
    }

    public String encrypt(String value) {
        try {
            final String KEY = this.aesKey;
            final String INIT_VECTOR = this.aesInitVector;
            System.out.println("KEY: " + KEY);
            System.out.println("INIT_VECTOR: " + INIT_VECTOR);
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            String base64String = Base64.encodeBase64URLSafeString(encrypted);

            System.out.println("encrypted string: "
                    + base64String);

            return base64String;
        } catch (Exception ex) {
            throw new BizRuntimeException("encrypt exception: value is " + value, ex);
        }
    }

    public String decrypt(String encoded){
        try {
            final String KEY = this.aesKey;
            final String INIT_VECTOR = this.aesInitVector;
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encoded));

            return new String(original);
        } catch (Exception ex) {
            throw new BizRuntimeException("decrypt exception: value is " + encoded, ex);
        }
    }

//    public static void main(String[] args) {
//        String str = new Date().getTime() + "--wxec002534a59ea2e7--002";
//        System.out.println("origin string: " + str);
//
//        try {
//            String encoded = encrypt(str);
//            System.out.println("encoded is: " + encoded + ", length is " + encoded.length());
//
//            String encodedUrl = URLEncoder.encode(encoded, "UTF-8");
//            System.out.println("after encoded url: " + encodedUrl);
//
//            String decodedUrl = URLDecoder.decode(encoded, "UTF-8");
//            System.out.println("after decoded url: " + decodedUrl);
//
//            System.out.println("decoded is: " + decrypt(decodedUrl));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
}
