package com.example.transfolio.domain.user.service;

import org.json.simple.JSONObject;

import java.util.HashMap;

public interface UserService {

    JSONObject SearchById(HashMap body);
    JSONObject Join(HashMap body);

}
