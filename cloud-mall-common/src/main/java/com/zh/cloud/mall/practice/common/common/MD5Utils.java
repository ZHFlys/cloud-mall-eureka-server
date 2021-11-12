package com.zh.cloud.mall.practice.common.common;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 */
public class MD5Utils {
    public static String getMD5Str(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((str + Constant.SALT).getBytes()));
    }

    public static void main(String[] args) {
        try {
            System.out.println(getMD5Str("1234"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
