package com.example.transfolio.domain.login.service;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.StringUtils;
import com.example.transfolio.domain.login.dao.LoginDao;
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

        if (hashMap == null) {
            return new ResObj(ErrorMessage.DATA_DUPL_ERROR).getObject();
        }

        return new ResObj(hashMap).getObject();
    }
}
