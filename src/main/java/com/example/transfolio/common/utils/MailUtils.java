package com.example.transfolio.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email 관련 Utils
 */

@Configuration
@Component
@Slf4j
public class MailUtils {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";


    /**
     * 이메일 메시지 내용 생성
     */
    public static String createText(String randomString) {

        StringBuffer sb = new StringBuffer();
        sb.append("이메일 인증을 인증번호입니다 <br>");
        sb.append("인증번호: " + randomString);

        return sb.toString();
    }

    /**
     * 이메일 형식 검증
     */
    public static boolean isValidEmail(String email) {

        if (CommonUtils.IsNullOrEmpty(email)) {
            return true;
        }

        // 이메일 Pattern 검증
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

