package com.example.transfolio.domain.user.service;

import org.json.simple.JSONObject;

import java.util.HashMap;

public interface UserService {

    JSONObject searchById(HashMap body);
    JSONObject join(HashMap body);

}
