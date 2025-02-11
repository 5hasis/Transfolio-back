package com.example.transfolio.career;

import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.career.entity.CareerEntity;
import com.example.transfolio.domain.career.model.CareerDto;
import com.example.transfolio.domain.career.repository.CareerRepository;
import com.example.transfolio.domain.career.service.CareerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class CareerTest {

    @Autowired
    CareerService careerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CareerRepository careerRepository;

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

    @Test
    @Transactional
    public void testDeleteCareer() throws Exception {

        Long careerPid = 1L; // 삭제할 경력 ID
        String loginId = "accountTest"; // 로그인된 사용자 ID
        String token = JwtUtil.createToken(loginId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정

        // 삭제할 경력 엔티티 생성
        CareerEntity careerEntity = CareerEntity.builder()
                .careerPid(careerPid)
                .careerTitle("제목 제목 경력 삭제 테스트")
                .careerContent("내용 내용 경력 삭제 테스트 내용")
                .careerDate("2024-11-01")
                .updatedAt(null)
                .userId(loginId)
                .build();

        // 실제 데이터베이스에 경력 저장 (테스트 데이터 준비)
        careerRepository.save(careerEntity);

        //when(careerService.deleteCareer(careerPid, loginId)).thenReturn(true); // 서비스 호출 mock

        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/career/delete/{careerPid}", careerPid)
                        .accept(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("jwtToken", token)) // JWT 인증을 위한 header

                )
                .andExpect(status().isNoContent()) // 삭제가 성공하면 204 응답
                .andDo(document(
                        "career/delete/{careerPid}", // 문서화할 이름
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters( // 경로 변수 설명
                                parameterWithName("careerPid").description("삭제할 경력 ID")
                        )
                ));
    }

    @Test
    public void testUpdateCareer() throws Exception {
        Long careerPid = 1L; // 수정할 경력 ID
        String loginId = "accountTest"; // 로그인된 사용자 ID
        String token = JwtUtil.createToken(loginId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정

        CareerDto careerDto = CareerDto.builder()
                .careerPid(careerPid)
                .careerTitle("[수정 테스트] 제목 제목 경력 테스트")
                .careerContent("[수정 테스트] 내용 내용 경력 추가 테스트 내용")
                .careerDate("20241101")
                .userId("accountTest")
                .build();

        //CareerEntity mockCareerEntity = new CareerEntity();

        this.mockMvc.perform(put("/career/edit", careerDto)
                        .cookie(new Cookie("jwtToken", token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(careerDto)))
                .andExpect(status().isOk())
                .andDo(document("career/edit",
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
        ;

    }
}
