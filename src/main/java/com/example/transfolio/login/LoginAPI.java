package com.example.transfolio.login;

import com.example.transfolio.common.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginAPI {


    // 로그인 중복 체크
    @GetMapping("/duplication/{id}")
    public String DuplicationID(@PathVariable String id) {
        String s = StringUtils.RandomString(6);

        return s;
    }

}
