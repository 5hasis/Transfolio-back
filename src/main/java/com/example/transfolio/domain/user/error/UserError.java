package com.example.transfolio.domain.user.error;

import com.example.transfolio.common.error.ErrorEnum;

public enum UserError implements ErrorEnum {
    DUPLICATE_USER_ID("001", "이미 존재하는 유저 아이디입니다"),

    FAIL_JOIN("002", "회원가입에 실패하였습니다")
    ;

    private String status = "200";
    private String message = "";

    UserError(String status, String message) {
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
