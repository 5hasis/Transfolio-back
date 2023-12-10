package com.example.transfolio.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class LoginServiceTest {

    @Test
    @DisplayName("랜덤 문자열 생성")
    public void RandomStringTest() {
        int length = 6;

        String stringCharachters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomStringIndex = random.nextInt(stringCharachters.length());
            char randomChar = stringCharachters.charAt(randomStringIndex);

            randomString.append(randomChar);
        }

        System.out.println(randomString);
    }
}
