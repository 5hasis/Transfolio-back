package com.example.transfolio.domain.auth.login;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.utils.CommonUtils;
import com.example.transfolio.common.utils.JwtUtils;
import com.example.transfolio.domain.auth.login.service.LoginService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class LoginAPI {

    @Value("${AUTH_LOGIN}")
    private String[] authLoginValid;

    private LoginService loginService;

    public LoginAPI(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public JSONObject authLogin(@RequestBody HashMap body) throws Exception {

        // Parameter 필수값 체크
        if (CommonUtils.validRequestParam(body, authLoginValid)) {
            return new ErrorObj(ErrorMessage.REQUIRED_PARAMETER_FIELD).getObject();
        }

        return loginService.login(body);
    }
}
