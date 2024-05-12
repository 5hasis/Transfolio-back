package com.example.transfolio.common.utils;


/*
문자열과 관련된 도구메서드
*/



import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Random;

public class StringUtils {



    /**
     * 랜덤한 문자열을 만들어주는 메서드
     * @param length 길이
     * @return 길이를 매개변수로 받은 랜덤한 숫자 + 문자
     */
    public static String randomString(int length) {
        String stringCharachters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 1; i < length; i++) {
            int randomStringIndex = random.nextInt(stringCharachters.length());
            char randomChar = stringCharachters.charAt(randomStringIndex);

            randomString.append(randomChar);
        }

        return randomString.toString();
    }


    /**
     * 빈값이거나 Null 체크
     */
    public static boolean isNullOrEmpty(String message) {

        if (message.equals("") || message == null) {
            return true;
        }

        return false;

    }

    /**
     * Bcrypt 암호화
     */
    public static String bcrypt(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt());

    }

    /**
     * Bcrypt로 암호화된 값 매칭
     */
    public static boolean checkBcrypt(String text, String hashText) {
        return BCrypt.checkpw(text, hashText);
    }
}
