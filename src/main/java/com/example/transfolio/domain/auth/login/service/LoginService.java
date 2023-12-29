package com.example.transfolio.domain.auth.login.service;

import org.json.simple.JSONObject;

import java.util.HashMap;

public interface LoginService {

    JSONObject login(HashMap body);
}
