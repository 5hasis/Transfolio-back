package com.example.transfolio.common.error;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.StringUtils;
import org.json.simple.JSONObject;

public enum ErrorMessage implements ErrorEnum {


    REQUIRED_PARAMETER_FIELD("001", "파라미터에 필수값이 존재하지 않습니다"),

    REQUIRED_BODY_FIELD("002", "BODY에 필수값이 존재하지 않습니다"),

    DUPLICATION_ID("003", "이미 존재하는 아이디입니다"),

    REQUIRED_ID_PASSWORD("004", "아이디 또는 비밀번호가 일치하지 않습니다"),

    EXPIRED_JWT_TOKEN("005", "만료된 토큰입니다"),

    UNAUTHORIZED_ACCESS("006","로그인이 필요합니다."),

    INVALID_JWT_USER("007","유효하지 않은 사용자 정보입니다."),

    USER_NOT_FOUND("008", "존재하지 않는 회원입니다")

    ;

    private String status = "200";
    private String message = "";

    ErrorMessage(String status, String message) {
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
