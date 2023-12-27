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

    private String message = "정상적으로 처리되었습니다";

    public ResObj(Builder build) {
        this.result = build.body;
        this.message = build.message;
    }

    public JSONObject getObject() {
        JSONObject jsonObj = new JSONObject();

        if (!CommonUtils.IsNullOrEmpty(result)) {
            jsonObj.put("result", result);
        }

        if (CommonUtils.IsNullOrEmpty(message)) {
            jsonObj.put("message", "정상적으로 처리되었습니다");
        }

        jsonObj.put("message", message);
        jsonObj.put("status", status);

        return jsonObj;

    }

    public static class Builder {

        private Object body;
        private String message;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder body(Object body) {
             this.body = body;
             return this;
        }

        public ResObj build() {
            return new ResObj(this);
        }
    }

}
