package com.example.vac.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class CommonUtil {
    private static final char[] CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MIN_LEN = 4;
    private static final int MAX_LEN = 10;

    public static String generate() {
        int length = RANDOM.nextInt(MAX_LEN - MIN_LEN + 1) + MIN_LEN;
        char[] buf = new char[length];
        for (int i = 0; i < length; i++) {
            buf[i] = CHARS[RANDOM.nextInt(CHARS.length)];
        }
        return new String(buf);
    }
}
