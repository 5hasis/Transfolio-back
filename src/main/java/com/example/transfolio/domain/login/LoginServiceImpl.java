package com.example.transfolio.domain.login;

import com.example.transfolio.common.response.ResObj;
import netscape.javascript.JSObject;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;

    public LoginServiceImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public JSObject SearchById(String id) {
        int id = loginDao.SearchId(id);

        return new ResObj(id).getObject();
    }
}
