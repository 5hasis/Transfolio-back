package com.example.transfolio.common.response;

import lombok.ToString;

import java.util.Objects;

@ToString
public class ResObj {

    private Object object;

    public ResObj(Object obj) {
        this.object = obj;
    }


    public Object getObject() {
        return this;
    }

}
