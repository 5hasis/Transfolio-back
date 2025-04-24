package com.example.transfolio.home;

import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.HomeController;
import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.repository.BoardRepository;
import com.example.transfolio.domain.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
}