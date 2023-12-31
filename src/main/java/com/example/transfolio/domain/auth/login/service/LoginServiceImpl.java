package com.example.transfolio.domain.auth.login.service;

import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.CommonUtils;
import com.example.transfolio.common.utils.JwtUtils;
import com.example.transfolio.common.utils.StringUtils;
import com.example.transfolio.domain.auth.login.error.LoginError;
import com.example.transfolio.domain.user.dao.UserDao;
import com.example.transfolio.domain.user.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    private UserDao userdao;

    public LoginServiceImpl(UserDao userdao) {
        this.userdao = userdao;
    }

    @Override
    public JSONObject login(HashMap body) {

        List<HashMap> selectUser = userdao.selectByUserId(body);

        // ID가 존재하지 않을경우
        if (CommonUtils.isZeroSize(selectUser)) {
            return new ErrorObj(LoginError.LOGIN_NOT_USERINFO).getObject();
        }

        // 중복된 ID가 존재할경우
        if (CommonUtils.isTwoOverSize(selectUser)) {
            return new ErrorObj(LoginError.LOGIN_TWO_USERINFO).getObject();
        }

        String userPassword = (String) body.get("userPassword");
        String userHashPassword  = (String) selectUser.get(0).get("userPassword");

        if (!StringUtils.checkBcrypt(userPassword, userHashPassword)) {
            return new ErrorObj(LoginError.LOGIN_PASSWORD_NOT_MATCH).getObject();
        };

        String jwtToken = JwtUtils.generateToken((String) body.get("userId"));

        return new ResObj.Builder()
                .body(jwtToken)
                .build().getObject();
    }
}
