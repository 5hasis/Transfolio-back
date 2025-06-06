package com.example.transfolio.profile;

import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.board.model.BoardFoldHistDto;
import com.example.transfolio.domain.board.model.BoardFoldResponseDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.career.model.CareerDto;
import com.example.transfolio.domain.career.service.CareerService;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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

    @MockBean
    private BoardService boardService;

    private String token;
    private Cookie jwtCookie;
    private final String userId = "accountTest";

    @Value("${jwt.secret}")
    String secretKey;

    @BeforeEach
    void setUp() {
        token = JwtUtil.createToken(userId, secretKey, 500000);
        jwtCookie = new Cookie("jwtToken", token);
    }

    @Test
    void getUserPortFolio() throws Exception{

        String jsonUserId = "{\"userId\":\"" + userId + "\"}";


        this.mockMvc.perform(post("/profile/portfolio")
                        .cookie(jwtCookie)
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

        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        // 가짜 데이터 정의
        List<CareerDto> fakeCareerList = Arrays.asList(
                new CareerDto(1L,"소년은 온다 번역", "번역번역번역번역번역번역번역번역번역번역번역번역", "2022-01-01", null, LocalDateTime.now(), "accountTest"),
                new CareerDto(2L,"고전시 번역 ~.~", "번역번역번역번역번역번역번역번역번역번역번역번역", "2021-05-10", LocalDateTime.parse("2021-05-11T00:00:00"), LocalDateTime.now(), "accountTest")
        );

        // when ~ thenReturn을 사용하여 서비스 메서드가 가짜 데이터를 반환하도록 설정
        when(careerService.getCareerListById(userId)).thenReturn(fakeCareerList);

        this.mockMvc.perform(post("/profile/career")
                        .cookie(jwtCookie)
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

        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        this.mockMvc.perform(post("/profile/myInfo")
                        .cookie(jwtCookie)
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

    @Test
    void getUserBookmark() throws Exception{

        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        // 가짜 데이터 정의
        List<BoardFoldResponseDto> fakeBookmarkList = Arrays.asList(
                new BoardFoldResponseDto(1L, LocalDateTime.of(2024, 2, 16, 12, 34, 56), "accountTest",
                        "게시물 제목", "게시물 부제목", 109),
                new BoardFoldResponseDto(2L, LocalDateTime.of(2024, 2, 10, 8, 20, 30), "accountTest",
                        "다른 게시물 제목", "다른 게시물 부제목", 78)
        );

        // when ~ thenReturn을 사용하여 서비스 메서드가 가짜 데이터를 반환하도록 설정
        when(boardService.getBookmarkListById(userId)).thenReturn(fakeBookmarkList);

        this.mockMvc.perform(post("/profile/bookmarks")
                        .cookie(jwtCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserId)
                )
                .andExpect(status().isOk())
                .andDo(document("profile/bookmarks",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                relaxedRequestFields(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                                ),
                        responseFields(
                                fieldWithPath("[].boardPid").description("게시물 ID"),
                                fieldWithPath("[].createdAt").description("게시물 접음 날짜"),
                                fieldWithPath("[].userId").description("글쓴이 ID"),
                                fieldWithPath("[].boardTitle").description("게시물 제목"),
                                fieldWithPath("[].boardSubTitle").description("게시물 부제목"),
                                fieldWithPath("[].foldCnt").description("접음 횟수")
                        ))
                );
    }
}
