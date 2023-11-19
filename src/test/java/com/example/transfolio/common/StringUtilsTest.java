package com.example.transfolio.common;

import com.example.transfolio.common.utils.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class StringUtilsTest {

    @Test
    @DisplayName("랜덤 문자열 생성")
    public void RandomStringTest() {
        int length = 6;

        String randomString = StringUtils.RandomString(length);

        Assertions.assertThat(randomString)
                .isNotNull()
                .isNotEmpty()
                .hasSize(length);
    }

}
