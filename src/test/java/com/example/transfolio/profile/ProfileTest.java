package com.example.transfolio.profile;

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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class ProfileTest {

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


        this.mockMvc.perform(post("/profile/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId)
                )
                .andExpect(status().isOk())
                .andDo(document("profile/portfolio",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(  // 쿼리 매개변수 문서화
                                        parameterWithName("userId").description("유저 아이디")
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
//        this.mockMvc.perform(post("/profile/career")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonUserId)
//                )
//                .andExpect(status().isOk())
//                .andDo(document("profile/career",
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

        this.mockMvc.perform(post("/profile/myInfo")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserId)
                )
                .andExpect(status().isOk())
                .andDo(document("profile/myInfo",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                relaxedRequestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                                )
                        )
                );
    }
}
