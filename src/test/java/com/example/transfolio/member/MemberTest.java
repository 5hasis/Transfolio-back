package com.example.transfolio.member;

import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.LoginDto;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserIntrsDto;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.transaction.Transactional;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class MemberTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Test
    void saveMember() throws Exception {

        UserIntrsDto userIntrs = new UserIntrsDto().builder()
                .intrsLanguage("한국어,영어,일본어")
                .intrsMajor("공학,전공")
                .intrsLiterature("고전시,고전소설")
                .intrsCorporation("공기업,사기업")
                .build();

        // GIVEN
        UserDto user = new UserDto().builder()
                .userId("accountTest")
                .email("test@test.account")
                .password("password")
                .userIntrsDto(userIntrs)
                .build();

        this.mockMvc.perform(post("/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(document(
                        "user/sign-up",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                                fieldWithPath("userIntrsDto.intrsLanguage").type(JsonFieldType.STRING).description("유저의 관심 언어"),
                                fieldWithPath("userIntrsDto.intrsMajor").type(JsonFieldType.STRING).description("유저의 관심 전공"),
                                fieldWithPath("userIntrsDto.intrsLiterature").type(JsonFieldType.STRING).description("유저의 관심 문학"),
                                fieldWithPath("userIntrsDto.intrsCorporation").type(JsonFieldType.STRING).description("유저의 관심 기업")
                        )));

    }

    @Test
    void signInMember() throws Exception {

        // GIVEN
        LoginDto login = new LoginDto().builder()
                .userId("accountTest")
                .password("password")
                .build();

        this.mockMvc.perform(post("/user/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andDo(document(
                        "user/sign-in",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호")
                        )));

    }

    @Test
    @Transactional
    void signOutMember() throws Exception {
        String userId = "accountTest";
        String token = JwtUtil.createToken(userId, "my-secret-key-123123", 500000); // 테스트용 JWT

        // 쿠키 설정
        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setPath("/"); // 쿠키 경로 설정
        cookie.setHttpOnly(true);
        //cookie.setSecure(false);

        // 로그아웃 API 호출 및 검증
        this.mockMvc.perform(post("/user/sign-out")
                        .cookie(cookie) // 로그아웃 요청에 JWT 토큰 쿠키 포함
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success")) // 수정된 메시지 검사
                .andExpect(jsonPath("$.status").value("200")) // 상태 코드도 확인
                .andExpect(jsonPath("$.result").value("로그아웃이 완료되었습니다."))
                .andDo(document(
                        "user/sign-out", // 문서화 식별자
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedResponseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("로그아웃 메시지")
                        )
                ));
    }


    @Test
    void updateUserIntrs() throws Exception{

        String userId = "accountTest";
        String token = JwtUtil.createToken(userId, "my-secret-key-123123", 500000); // 테스트용 JWT

        UserIntrsDto userIntrs = new UserIntrsDto().builder()
                .intrsLanguage("러시아어")
                .intrsMajor("예체능,인문")
                .intrsLiterature("유럽문학")
                .intrsCorporation("엔터테인먼트")
                .build();

        UserDto userDto = new UserDto().builder()
                .userId("accountTest")
                .userIntrsDto(userIntrs)
                .build();


        this.mockMvc.perform(put("/user/edit/intrs")
                        .cookie(new Cookie("jwtToken", token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())  // 응답 상태 코드가 200 OK인지를 검증
                .andDo(document(  // Spring REST Docs 문서화
                        "user/edit/intrs",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedRequestFields(  // 요청 필드 설명
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디"),
                                fieldWithPath("userIntrsDto.intrsLanguage").type(JsonFieldType.STRING).description("유저의 관심 언어"),
                                fieldWithPath("userIntrsDto.intrsMajor").type(JsonFieldType.STRING).description("유저의 관심 전공"),
                                fieldWithPath("userIntrsDto.intrsLiterature").type(JsonFieldType.STRING).description("유저의 관심 문학"),
                                fieldWithPath("userIntrsDto.intrsCorporation").type(JsonFieldType.STRING).description("유저의 관심 기업")
                        )
                ));

    }

}
