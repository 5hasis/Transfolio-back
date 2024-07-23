package com.example.transfolio.member;

import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.LoginDto;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserIntrsDto;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void getMyPost() throws Exception{

        String userId = "accountTest";
        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        String token = JwtUtil.createToken(userId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정

        this.mockMvc.perform(post("/mypage/")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserId)
                )
                .andExpect(status().isOk())
                .andDo(document("mypage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedRequestFields(
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                        )
                )
        );

}
}
