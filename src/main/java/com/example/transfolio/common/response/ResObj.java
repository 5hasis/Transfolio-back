package com.example.transfolio.common.response;

import com.example.transfolio.common.utils.CommonUtils;
import com.example.transfolio.common.utils.StringUtils;
import lombok.ToString;
import org.json.simple.JSONObject;

import java.util.Objects;


/**
 * ===================================================
 * RESPONSE OBJECT
 *
 * EXAMPLE: new ResObj.Builder().build.getObject()
 * message: 메시지 세팅
 * body: RESPONSE로 보여줄 아이템
 *
 * ===================================================
 */

@ToString
public class ResObj {

    private Object result;

    private String status = "200";

    private String message = "success";

    public ResObj() {

    }

    public ResObj(Object result) {
        this.result = result;
    }

    public ResObj(String status, String message) {
        this.status = status;
        this.message = message;

    }

    public JSONObject getObject() {
        JSONObject jsonObj = new JSONObject();

        if (!CommonUtils.isNullOrEmpty(result)) {
            jsonObj.put("result", result);
        }

        jsonObj.put("message", this.message);
        jsonObj.put("status", status);

        return jsonObj;

    }

    public String getStatus() {
        return status;
    }

}



