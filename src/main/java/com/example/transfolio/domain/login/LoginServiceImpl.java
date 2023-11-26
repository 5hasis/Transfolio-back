package com.example.transfolio.domain.login;

import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;

    public LoginServiceImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public int SearchById(String id) {
        return loginDao.SearchId(id);
    }
}
