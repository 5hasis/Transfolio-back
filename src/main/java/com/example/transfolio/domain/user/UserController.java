package com.example.transfolio.domain.user;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.user.entity.User;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.example.transfolio.domain.user.service.UserSerivce;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        System.out.println("httpHeaders = " + httpHeaders);

        return userSerivce.registerUser(user);
    }


    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public JSONObject loginUser(@RequestBody UserDto user, @RequestHeader HttpHeaders httpHeaders) {

//        Optional<List<String>> trafTokenListOptional = Optional.ofNullable(httpHeaders.get("Traf_Token"));
//        boolean hasTrafToken = trafTokenListOptional.isPresent();
//        String trafToken = hasTrafToken ? trafTokenListOptional.get().get(0) : "";
//
//        if (!trafToken.isEmpty()) {
//            boolean expired = JwtUtil.isExpired(trafToken, "my-secret-key-123123");
//            if (expired) {
//                return new ErrorObj(ErrorMessage.EXPIRED_JWT_TOKEN).getObject();
//            }
//        }

        return userSerivce.login(user);
    }


}
