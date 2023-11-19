package com.example.transfolio.common.utils;


/*
문자열과 관련된 도구메서드
*/

import java.util.Random;

public class StringUtils {



    /**
     * 랜덤한 문자열을 만들어주는 메서드
     * @param length 길이
     * @return 길이를 매개변수로 받은 랜덤한 숫자 + 문자
     */
    public static String RandomString(int length) {
        String stringCharachters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomStringIndex = random.nextInt(stringCharachters.length());
            char randomChar = stringCharachters.charAt(randomStringIndex);

            randomString.append(randomChar);
        }

        return randomString.toString();
    }
}
