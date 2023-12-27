package com.example.transfolio.domain.auth.email.service;

import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.MailUtils;
import com.example.transfolio.common.utils.StringUtils;
import com.example.transfolio.domain.auth.email.error.EmailError;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@Service
@Slf4j
public class EmailService {

    @Value("${AUTH_EMAIL}")
    private String[] userEmailKey;

    private String mailTitle = "제목";
    private String from = "wkdgus1136@gmail.com";

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 이메일 발송
     */
    public JSONObject sendEmail(HashMap body) {
        String reandomString = StringUtils.RandomString(6);

        String content = MailUtils.createText(reandomString);
        String userEmail = (String) body.get(userEmailKey[0]);

        /* 이메일 형식 검증
        if (MailUtils.isValidEmail(userEmail)) {
            return new ErrorObj(EmailError.FAIL_NOT_MATCH_EMAIL).getObject();
        }
         */

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mailHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            log.info("=== 메일 전송 시작 ===");

            mailHelper.setFrom(from);
            log.info("발신자 이메일 세팅: " + from);

            mailHelper.setTo(userEmail);
            log.info("수신자 전송 시작: " + userEmail);

            mailHelper.setSubject(mailTitle);
            log.info("이메일 타이틀: " + mailTitle);

            mailHelper.setText(content, true);

        } catch (Exception e) {
            log.debug("=== 이메일 발송 실패 ===");
            return new ErrorObj(EmailError.FAIL_SEND_EMAIL).getObject();
        }

        mailSender.send(mimeMessage);

        return new ResObj.Builder()
                .message("발송 인증번호 : " + reandomString)
                .build().getObject();
    }
}
