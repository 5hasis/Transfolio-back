package com.example.transfolio.common.error;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.StringUtils;
import org.json.simple.JSONObject;

public enum ErrorMessage implements ErrorEnum {


    REQUIRED_PARAMETER_FIELD("001", "파라미터에 필수값이 존재하지 않습니다"),

    REQUIRED_BODY_FIELD("002", "BODY에 필수값이 존재하지 않습니다")
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
