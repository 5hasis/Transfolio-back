package com.example.transfolio.domain.user;

import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.service.UserSerivce;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserSerivce userSerivce;

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public JSONObject registerUser(@RequestBody UserDto user, @RequestHeader HttpHeaders httpHeaders) {

        return userSerivce.registerUser(user);
    }


    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public JSONObject loginUser(@RequestBody UserDto user, @RequestHeader HttpHeaders httpHeaders) {


        return userSerivce.login(user);
    }


}
