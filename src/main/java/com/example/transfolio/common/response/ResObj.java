package com.example.transfolio.common.response;

import com.example.transfolio.common.utils.StringUtils;
import lombok.ToString;
import org.json.simple.JSONObject;

import java.util.Objects;

@ToString
public class ResObj {

    private Object body;

    private String status = "200";

    private String message = "";

    public ResObj(Object obj) {
        this.body = obj;
    }

    public ResObj(Object obj, String message) {
        this.body = obj;
        this.message = message;
    }


    public JSONObject getObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Object", this.body);
        jsonObject.put("status", this.status);

        if (StringUtils.IsNullOrEmpty(this.message)) {
            jsonObject.put("message", this.message);
        }

        return jsonObject;
    }

}
