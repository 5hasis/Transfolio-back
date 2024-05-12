package com.example.transfolio.domain.user.service;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.user.entity.User;
import com.example.transfolio.domain.user.entity.UserIntrs;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserIntrsDto;
import com.example.transfolio.domain.user.repository.UserIntrsRepository;
import com.example.transfolio.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSerivce {

    private final UserRepository userRepository;
    private final UserIntrsRepository userIntrsRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    /**
     * 회원가입
     */
    public JSONObject registerUser(UserDto userDto) {

        if (!userRepository.findByUserId(userDto.getUserId()).isEmpty()) {
            return new ErrorObj(ErrorMessage.DUPLICATION_ID).getObject();
        }

        String userPassword = userDto.getPassword();
        String encodePassword = encoder.encode(userPassword);

        User build = User
                .builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(encodePassword)
                .build();

        userRepository.save(build);

        UserIntrs userIntrs = UserIntrs
                .builder()
                .userId(userDto.getUserId())
                .intrsLanguage(userDto.getUserIntrsDto().getIntrsLanguage())
                .intrsMajor(userDto.getUserIntrsDto().getIntrsMajor())
                .intrsCorporation(userDto.getUserIntrsDto().getIntrsCorporation())
                .intrsLiterature(userDto.getUserIntrsDto().getIntrsLiterature())
                .build();

        userIntrsRepository.save(userIntrs);

        return new ResObj().getObject();

    }

    /**
     * 로그인
     */
    public JSONObject login(UserDto userDto) {

        List<User> byUserId = userRepository.findByUserId(userDto.getUserId());
        User user = byUserId.get(0);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            return null;
        }

        // 로그인 성공 => Jwt Token 발급

        String secretKey = "my-secret-key-123123";
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        String jwtToken = JwtUtil.createToken(user.getUserId(), secretKey, expireTimeMs);

        return new ResObj(jwtToken).getObject();

    }


}
