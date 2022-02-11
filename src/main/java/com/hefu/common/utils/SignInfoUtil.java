package com.hefu.common.utils;

import lombok.Builder;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * websocket 链接认证，即双方拿到共同的macAd进行加密，然后比较加密后是否相等
 * @author Administrator
 */
@Data
@Builder
public class SignInfoUtil {

    private String nonce_str;
    private String timestamp;
    private String signature;
    private String macAd;

    public boolean check(String url){

        StringBuilder builder=new StringBuilder();
        builder.append("hefu_api_ticket=");
        builder.append(macAd);
        builder.append("&noncestr=");
        builder.append(nonce_str);
        builder.append("&timestamp=");
        builder.append(timestamp);
        builder.append("&url=");
        builder.append(url);

        String signature="";

        MessageDigest crypt;

        try {
            crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(builder.toString().getBytes(StandardCharsets.UTF_8));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return this.signature.equals(signature);
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
