package com.example.transfolio.domain.login;

import com.example.transfolio.domain.login.service.LoginService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginAPI {

    private final LoginService loginService;

    public LoginAPI(LoginService loginService) {
        this.loginService = loginService;
    }

    // 로그인 중복 체크
    @PostMapping("/dupl")
    public JSONObject DuplicationID(@RequestBody HashMap body) {

        return loginService.SearchById(body);
    }

    // 이메일 인증
    @PostMapping("/auth/email")
    public String LoginAuthEmail(@RequestBody String email) {
        return "";
    }

    // 회원가입
    @PostMapping("/")
    public String LoginSave(@RequestBody String info) {
        return "";
    }

}
