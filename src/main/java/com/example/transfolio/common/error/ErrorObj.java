package com.example.transfolio.common.error;

import org.json.simple.JSONObject;

public class ErrorObj {


    private String status = "200";
    private String message = "에러가 발생하였습니다";

    public ErrorObj(ErrorEnum errorEnum) {
        this.status = errorEnum.getStatus();
        this.message = errorEnum.getMessage();
    }

    public JSONObject getObject() {
        JSONObject errorObject = new JSONObject();

        errorObject.put("message", this.message);
        errorObject.put("status", this.status);

        return errorObject;
    }
}
