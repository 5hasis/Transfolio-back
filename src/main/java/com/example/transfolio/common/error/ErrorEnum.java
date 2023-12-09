package com.example.transfolio.common.error;

import org.json.simple.JSONObject;

public interface ErrorEnum {

    Object getObject();
    String getStatus();
    String getMessage();
}
