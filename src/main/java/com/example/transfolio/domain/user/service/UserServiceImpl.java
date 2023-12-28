package com.example.transfolio.domain.user.service;

import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.CommonUtils;
import com.example.transfolio.common.utils.MailUtils;
import com.example.transfolio.common.utils.StringUtils;
import com.example.transfolio.domain.user.dao.UserDao;
import com.example.transfolio.domain.user.error.UserError;
import org.json.simple.JSONObject;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final JavaMailSender mailSender;


    public UserServiceImpl(UserDao loginDao, JavaMailSender mailSender) {
        this.userDao = loginDao;
        this.mailSender = mailSender;
    }

    /**
     * 아이디 중복 체크
     */
    @Override
    public JSONObject searchById(HashMap params) {

        List<HashMap> searchIdList = userDao.selectByUserId(params);

        // 이미 존재하는 ID가 있을경우
        if (!CommonUtils.isZeroSize(searchIdList)) {
            return new ErrorObj(UserError.DUPLICATE_USER_ID).getObject();
        }

        return new ResObj.Builder()
                .message("가입가능한 ID 입니다")
                .body(params)
                .build().getObject();
    }

    @Override
    public JSONObject join(HashMap body) {

        List<HashMap> searchIdList = userDao.selectByUserId(body);

        // 이미 존재하는 ID가 있을경우
        if (!CommonUtils.isZeroSize(searchIdList)) {
            return new ErrorObj(UserError.DUPLICATE_USER_ID).getObject();
        }

        // 비밀번호 평문 Bcrypt 암호화
        String bcryptUserPassword = StringUtils.bcrypt( (String) body.get("userPassword") );
        body.put("userPassword", bcryptUserPassword);
        int insertResult = userDao.insertUserInfo(body);

        // 회원가입 실패
        if (insertResult == 0) {
            return new ErrorObj(UserError.FAIL_JOIN).getObject();
        }

        return new ResObj.Builder()
                .body(body)
                .build().getObject();
    }
}
