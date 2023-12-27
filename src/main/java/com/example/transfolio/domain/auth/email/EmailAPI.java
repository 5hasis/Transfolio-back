package com.example.transfolio.domain.auth.email;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.utils.CommonUtils;
import com.example.transfolio.domain.auth.email.service.EmailService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class EmailAPI {

    @Value("${AUTH_EMAIL}")
    private String[] authEmailValid;

    private final EmailService emailService;

    public EmailAPI(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     이메일 인증
     */
    @PostMapping("/email")
    public JSONObject LoginAuthEmail(@RequestBody HashMap body) {

        // Parameter 필수값 체크
        if (CommonUtils.ValidRequestParam(body, authEmailValid)) {
            return new ErrorObj(ErrorMessage.REQUIRED_BODY_FIELD).getObject();
        }

        return emailService.sendEmail(body);
    }

}
