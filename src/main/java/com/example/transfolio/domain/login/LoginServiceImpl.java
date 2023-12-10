package com.example.transfolio.domain.login;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.StringUtils;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;

    public LoginServiceImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public JSONObject SearchById(HashMap body) {


        HashMap hashMap = loginDao.SearchId(body);

        return new ResObj(hashMap, "사용가능한 ID 입니다").getObject();
    }
}
