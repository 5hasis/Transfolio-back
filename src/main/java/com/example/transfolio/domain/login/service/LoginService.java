package com.example.transfolio.domain.login.service;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

import java.util.HashMap;

public interface LoginService {

    JSONObject SearchById(HashMap body);

}
