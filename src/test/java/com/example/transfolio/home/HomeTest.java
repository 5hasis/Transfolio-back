package com.example.transfolio.home;

import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class HomeTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    BoardRepository boardRepository;

    private String token;
    private Cookie jwtCookie;
    private final String userId = "accountTest";

    @Value("${jwt.secret}")
    String secretKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        token = JwtUtil.createToken(userId, secretKey, 500000);
        jwtCookie = new Cookie("jwtToken", token);
    }

    @Test
    public void initialHome() throws Exception {

    }

    @Test
    public void getHomeIntrsPost() throws Exception {

        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        this.mockMvc.perform(post("/homeIntrs")
                        .cookie(jwtCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserId)
                )
                .andExpect(status().isOk())
                .andDo(document("homeIntrs",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            relaxedRequestFields(
                                    fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                            )
                        )
                );
    }

    @Test
    public void testGetTop9BoardsByFoldCntWithRestDocs() throws Exception {
        this.mockMvc.perform(get("/todaysTranslator")
                        .cookie(jwtCookie)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.length()").value(9))  // 상위 9개 게시글만
                .andDo(print())
                .andDo(document("todaysTranslator",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    @Transactional
    public void testGetRecommendedTranslatorset() throws Exception {

        String testCtg = "전공"; // 조회할 카테고리

        this.mockMvc.perform(get("/translators/recommendations")
                        .cookie(jwtCookie)
                        .param("category", testCtg) // @RequestParam 파라미터 추가
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(
                        "translators/recommendations", // 문서 이름
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters( // 쿼리 파라미터 설명
                                parameterWithName("category").description("게시물 카테고리(대분류)")
                        ),
                        relaxedResponseFields( // 응답 필드 설명
                                fieldWithPath("[]").description("추천 통역가/번역가 (가장 찜하기 많은 게시물 작성 회원) 정보 리스트"),
                                fieldWithPath("[].userId").type(JsonFieldType.STRING).description("회원 아이디"),
                                fieldWithPath("[].email").type(JsonFieldType.STRING).description("회원 아이디")
                        )
                ));
    }

}
