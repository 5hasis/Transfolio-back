package com.example.transfolio.domain.user;

import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.service.UserSerivce;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private UserSerivce userSerivce;

    /**
     * 마이페이지 조회
     */
    @PostMapping("/")
    public JSONObject getMyInfo(@RequestBody UserDto userDto) {
        //본인이 맞는지 확인-

        return userSerivce.findUser(userDto);
    }
}
