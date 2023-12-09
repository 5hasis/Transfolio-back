package com.example.transfolio.common.error;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.StringUtils;
import org.json.simple.JSONObject;

public enum ErrorMessage implements ErrorEnum {

    DATA_DUPL_ERROR("0001", "중복된 데이터 입니다"),
    ;

    private String status = "200";
    private String message = "";

    ErrorMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public JSONObject getObject() {
        JSONObject errorObj = new JSONObject();

        errorObj.put("status", this.status);
        errorObj.put("message", this.message);

        return errorObj;
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
