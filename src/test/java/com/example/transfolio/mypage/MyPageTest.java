package com.example.transfolio.mypage;

import com.example.transfolio.common.utils.JwtUtil;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class MyPageTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMyPortFolio() throws Exception{

        String userId = "accountTest";
        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        String token = JwtUtil.createToken(userId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정

        this.mockMvc.perform(post("/mypage/portfolio")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserId)
                )
                .andExpect(status().isOk())
                .andDo(document("mypage/portfolio",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                relaxedRequestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                                )
                        )
                );
    }

    @Test
    void getMyCareer() throws Exception{

//        String userId = "accountTest";
//        String jsonUserId = "{\"userId\":\"" + userId + "\"}";
//
//        String token = JwtUtil.createToken(userId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정
//
//        this.mockMvc.perform(post("/mypage/career")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonUserId)
//                )
//                .andExpect(status().isOk())
//                .andDo(document("mypage/career",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                relaxedRequestFields(
//                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
//                                )
//                        )
//                );
    }

    @Test
    void getMyInfo() throws Exception{

        String userId = "accountTest";
        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        String token = JwtUtil.createToken(userId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정

        this.mockMvc.perform(post("/mypage/myInfo")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserId)
                )
                .andExpect(status().isOk())
                .andDo(document("mypage/myInfo",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                relaxedRequestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                                )
                        )
                );
    }
}
