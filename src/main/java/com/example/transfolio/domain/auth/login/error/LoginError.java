package com.example.transfolio.domain.auth.login.error;

import com.example.transfolio.common.error.ErrorEnum;

public enum LoginError implements ErrorEnum {
    LOGIN_NOT_USERINFO("001", "존재하지 않는 아이디입니다"),

    LOGIN_TWO_USERINFO("002", "중복된 아이디가 존재합니다"),

    LOGIN_PASSWORD_NOT_MATCH("003", "비밀번호가 일치하지 않습니다")

    ;

    private String status = "200";
    private String message = "";

    LoginError(String status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
