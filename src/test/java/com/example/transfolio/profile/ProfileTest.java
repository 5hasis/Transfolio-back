package com.example.transfolio.profile;

import com.example.transfolio.domain.career.model.CareerDto;
import com.example.transfolio.domain.career.service.CareerService;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
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

    @MockBean
    private CareerService careerService;

    @Test
    void getUserPortFolio() throws Exception{

        String userId = "accountTest";
        String jsonUserId = "{\"userId\":\"" + userId + "\"}";


        this.mockMvc.perform(post("/profile/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserId)
                )
                .andExpect(status().isOk())
                .andDo(document("profile/portfolio",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                relaxedRequestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                                )
                        )
                );
    }

    @Test
    void getUserCareer() throws Exception{

        String userId = "accountTest";
        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        // 가짜 데이터 정의
        List<CareerDto> fakeCareerList = Arrays.asList(
                new CareerDto("소년은 온다 번역", "번역번역번역번역번역번역번역번역번역번역번역번역", "2022-01-01", null, LocalDateTime.now(), "accountTest"),
                new CareerDto("고전시 번역 ~.~", "번역번역번역번역번역번역번역번역번역번역번역번역", "2021-05-10", LocalDateTime.parse("2021-05-11T00:00:00"), LocalDateTime.now(), "accountTest")
        );

        // when ~ thenReturn을 사용하여 서비스 메서드가 가짜 데이터를 반환하도록 설정
        when(careerService.getCareerListById(userId)).thenReturn(fakeCareerList);

        this.mockMvc.perform(post("/profile/career")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserId)
                )
                .andExpect(status().isOk())
                .andDo(document("profile/career",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                relaxedRequestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                                )
                        )
                );
    }

    @Test
    void getUserInfo() throws Exception{

        String userId = "accountTest";
        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        //String token = JwtUtil.createToken(userId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정

        this.mockMvc.perform(post("/profile/myInfo")
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
