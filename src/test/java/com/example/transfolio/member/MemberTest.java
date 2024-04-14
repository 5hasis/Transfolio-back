package com.example.transfolio.member;

import com.example.transfolio.domain.user.entity.User;
import com.example.transfolio.domain.user.entity.UserIntrs;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserIntrsDto;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
    @DisplayName("회원 가입")
    @Transactional
    void saveMember() throws Exception {

        UserIntrsDto userIntrsDto = new UserIntrsDto().builder()
                .userPid(999999L)
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
                .userIntrsDto(userIntrsDto)
                .build();

        this.mockMvc.perform(post("/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(document(
                        "user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                                fieldWithPath("userIntrsDto.userPid").type(JsonFieldType.STRING).description("").ignored(),
                                fieldWithPath("userIntrsDto.intrsLanguage").type(JsonFieldType.STRING).description("유저의 관심 언어"),
                                fieldWithPath("userIntrsDto.intrsMajor").type(JsonFieldType.STRING).description("유저의 관심 전공"),
                                fieldWithPath("userIntrsDto.intrsLiterature").type(JsonFieldType.STRING).description("유저의 관심 문학"),
                                fieldWithPath("userIntrsDto.intrsCorporation").type(JsonFieldType.STRING).description("유저의 관심 기업")
                        )));

    }
}
