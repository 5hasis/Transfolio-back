package com.example.transfolio.common.error;

import org.json.simple.JSONObject;

public class ErrorObj {

    private final String status;
    private final String error;

    public ErrorObj(ErrorEnum errorEnum) {
        this.status = errorEnum.getStatus();
        this.error = errorEnum.getMessage();
    }

    public JSONObject getObject() {
        JSONObject errorObject = new JSONObject();

        errorObject.put("message", this.error);
        errorObject.put("status", this.status);

        return errorObject;
    }
}
