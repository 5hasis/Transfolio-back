package com.example.transfolio.domain.user.service;

import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;
import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.user.entity.UserEntity;
import com.example.transfolio.domain.user.entity.UserIntrsEntity;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserInfoDto;
import com.example.transfolio.domain.user.model.UserIntrsDto;
import com.example.transfolio.domain.user.repository.UserIntrsRepository;
import com.example.transfolio.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSerivce {

    private final UserRepository userRepository;
    private final UserIntrsRepository userIntrsRepository;
    private final PasswordEncoder encoder;

    /**
     * 회원가입
     */
    public JSONObject registerUser(UserDto userDto) {

        if (!userRepository.findByUserId(userDto.getUserId()).isEmpty()) {
            return new ErrorObj(ErrorMessage.DUPLICATION_ID).getObject();
        }

        String userPassword = userDto.getPassword();
        String encodePassword = encoder.encode(userPassword);

        UserEntity build = UserEntity
                .builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(encodePassword)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(build);

        UserIntrsEntity userIntrs = UserIntrsEntity
                .builder()
                .user(build)
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
    public JSONObject login(UserDto userDto, HttpServletResponse response) {

        List<UserEntity> userList = userRepository.findByUserId(userDto.getUserId());

        boolean isSearchUser = userList.isEmpty();
        if(isSearchUser) {
            return new ErrorObj(ErrorMessage.REQUIRED_ID_PASSWORD).getObject();
        }

        UserEntity user = userList.get(0);

        boolean isPasswordMatches = encoder.matches(userDto.getPassword(), user.getPassword());
        if (!isPasswordMatches) {
            return new ErrorObj(ErrorMessage.REQUIRED_ID_PASSWORD).getObject();
        }

        // 로그인 성공 => Jwt Token 발급
        String secretKey = "my-secret-key-123123";
        long expireTimeMs = 5000000;     // Token 유효 시간 = 60분

        String jwtToken = JwtUtil.createToken(user.getUserId(), secretKey, expireTimeMs);

        //response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);


        // ResponseCookie 사용 (기존 Cookie 방식 개선)
        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwtToken)
                .httpOnly(true)  // JavaScript 접근 불가
                .sameSite("None") // 크로스 도메인 허용
                .secure(true) // HTTPS 에서만 사용
                .path("/") // 모든 경로에서 접근 가능
                .maxAge(Duration.ofHours(1)) // 쿠키 유효 시간: 1시간
                //.domain("front-translate-web.vercel.app") // 쿠키 도메인 설정
                .build();

        // 응답 헤더에 쿠키 추가
        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        JSONObject jsonObject = new ResObj(jwtToken).getObject();
        jsonObject.put("userId", user.getUserId());
        jsonObject.put("email", user.getEmail());

        return jsonObject;

    }


    /**
     * 회원정보 조회
     */
    @Transactional
    public UserInfoDto getUserByUserId(String loginId){
        return userRepository.findInfoByUserId(loginId);
    }

    /**
     * 로그아웃
     */
    public JSONObject logout(HttpServletResponse response) {

        // JWT 토큰을 담고 있는 쿠키 삭제 (즉시 만료)
        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", "")
                //.domain("front-translate-web.vercel.app") // 쿠키 도메인 설정
                .httpOnly(true)  // JavaScript 접근 불가
                .sameSite("None") // 크로스 도메인 허용
                .secure(true) // HTTPS 에서만 사용
                .path("/") // 모든 경로에서 접근 가능
                .maxAge(0) // 쿠키 즉시 만료 (삭제)
                .build();

        // 응답 헤더에 쿠키 삭제 요청 추가
        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());


        // 로그아웃 성공 메시지 반환
        return new ResObj("로그아웃이 완료되었습니다.").getObject();
    }

    @Transactional
    public ResObj updateUserIntrs(UserDto userDto){

        // userId로 UserEntity 조회
        UserEntity user = userRepository.findByUserId(userDto.getUserId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserIntrsEntity userIntrs = userIntrsRepository.findByUser(user)
                .orElseGet(() -> UserIntrsEntity.builder().user(user).build());

        UserIntrsDto userIntrsDto = userDto.getUserIntrsDto();

        userIntrs.setIntrsLanguage(userIntrsDto.getIntrsLanguage());
        userIntrs.setIntrsMajor(userIntrsDto.getIntrsMajor());
        userIntrs.setIntrsCorporation(userIntrsDto.getIntrsCorporation());
        userIntrs.setIntrsLiterature(userIntrsDto.getIntrsLiterature());

        UserIntrsEntity save = userIntrsRepository.save(userIntrs);

        return new ResObj(save);
    }


}
