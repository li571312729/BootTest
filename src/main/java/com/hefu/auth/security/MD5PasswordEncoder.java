package com.hefu.auth.security;

import com.hefu.common.utils.Md5Util;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("md5");
            return Base64.encodeBase64String(messageDigest.digest(rawPassword.toString().getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
       return (encodedPassword).equals(Md5Util.encode(rawPassword.toString()));
    }

    public static void main(String[] args) {
        System.out.println(new MD5PasswordEncoder().encode("mk123456"));
    }
}
