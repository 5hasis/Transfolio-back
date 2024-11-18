package com.example.transfolio.career;

import com.example.transfolio.domain.career.entity.CareerEntity;
import com.example.transfolio.domain.career.service.CareerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriHost = "3.36.105.195", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest
public class CareerTest {

    @Autowired
    CareerService careerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateCareer() throws Exception {
        CareerEntity careerEntity = CareerEntity.builder()
                .careerTitle("제목 제목 경력 추가 테스트")
                .careerContent("내용 내용 경력 추가 테스트 내용")
                .careerDate("20241101")
                .updatedAt(null)
                .userId("accountTest")
                .build();

        this.mockMvc.perform(post("/career/regist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(careerEntity)))
                .andExpect(status().isCreated())
                .andDo(document(
                        "career/regist",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("careerPid").description("경력 ID"),
                                fieldWithPath("careerTitle").description("경력 제목"),
                                fieldWithPath("careerContent").description("경력 내용"),
                                fieldWithPath("careerDate").description("경력 날짜"),
                                fieldWithPath("updatedAt").description("수정일").optional(),
                                fieldWithPath("createdAt").description("작성일"),
                                fieldWithPath("userId").description("작성자 ID")
                        )))
                .andExpect(jsonPath("$.careerTitle").value("제목 제목 경력 추가 테스트"))
                .andExpect(jsonPath("$.careerContent").value("내용 내용 경력 추가 테스트 내용")
                );
    }
}
