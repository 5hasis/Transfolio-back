package com.example.transfolio.domain.user;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.utils.CommonUtils;
import com.example.transfolio.domain.user.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserAPI {

    @Value("${USER_SEARCH}")
    private String[] userSearchValid;

    @Value("${USER_JOIN}")
    private String[] userJoinValid;

    private final UserService userService;
    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    /**
     아이디 중복 체크
     */
    @GetMapping("/search")
    public JSONObject DuplicationID(@RequestParam HashMap param) {

        // Parameter 필수값 체크
        if (CommonUtils.ValidRequestParam(param, userSearchValid)) {
           return new ErrorObj(ErrorMessage.REQUIRED_PARAMETER_FIELD).getObject();
        }

        return userService.SearchById(param);
    }


    // 회원가입
    @PostMapping("/join")
    public JSONObject LoginSave(@RequestBody HashMap body) {

        // Parameter 필수값 체크
        if (CommonUtils.ValidRequestParam(body, userJoinValid)) {
            return new ErrorObj(ErrorMessage.REQUIRED_PARAMETER_FIELD).getObject();
        }

         return userService.Join(body);
    }

}
