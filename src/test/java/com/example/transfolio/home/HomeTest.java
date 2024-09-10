package com.example.transfolio.home;

import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.HomeController;
import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class HomeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    private List<BoardEntity> mockInitialPosts;
    private Page<BoardEntity> mockPagedPosts;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initialHome() throws Exception {

    }

    @Test
    public void getHomeIntrsPost() throws Exception {
        String userId = "accountTest";
        String jsonUserId = "{\"userId\":\"" + userId + "\"}";

        String token = JwtUtil.createToken(userId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정

        this.mockMvc.perform(post("/homeIntrs")
                        .header("Authorization", "Bearer " + token)
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
    public void getTodayTranslate() throws Exception {

        /*//구현 진행중 ...

        this.mockMvc.perform(post("/todayTranslate")
                )
                .andExpect(status().isOk())
                .andDo(document("/todayTranslate",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );*/
    }
}