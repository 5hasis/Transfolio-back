package com.example.transfolio.domain.user;

import com.example.transfolio.domain.user.entity.User;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.example.transfolio.domain.user.service.UserSerivce;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserSerivce userSerivce;

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public JSONObject registerUser(@RequestBody UserDto user) {

        return userSerivce.registerUser(user);
    }


    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public JSONObject loginUser(@RequestBody UserDto user) {

        return userSerivce.login(user);
    }


}
