package com.example.transfolio.domain.auth.email.error;

import com.example.transfolio.common.error.ErrorEnum;

public enum EmailError implements ErrorEnum {

    FAIL_NOT_MATCH_EMAIL("001", "이메일 형식이 맞지 않습니다"),

    FAIL_SEND_EMAIL("002", "이메일 발송에 실패하였습니다")
    ;

    private String status = "200";
    private String message = "";

    EmailError(String status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
