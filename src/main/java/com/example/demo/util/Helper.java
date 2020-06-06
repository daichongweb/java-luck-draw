package com.example.demo.util;

import org.apache.commons.lang3.RandomUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Helper {

    // md5加密
    public String md5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(str.getBytes());
        byte[] llll = md.digest();
        BigInteger bigInteger = new BigInteger(1, llll);
        String tmp;
        for (tmp = bigInteger.toString(16); tmp.length() < 32; tmp = "0" + tmp) {
            ;
        }
        return tmp;
    }

    // 生成随机字符串
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    // 生成随机数字
    public long[] getRandom(long range, long length) {
        long min = 1;
        long max = range;
        long[] rands = new long[(int) length];
        for (int i = 1; i < length; i++) {
            rands[i] = RandomUtils.nextLong(min, max);
        }
        return rands;
    }
}
