package com.example.transfolio.board;

import com.example.transfolio.domain.LoginDto;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.repository.BoardRepository;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserIntrsDto;
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

import javax.persistence.EntityManager;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerBoard() throws Exception {

        BoardDto board = new BoardDto().builder()
                .userId("test")
                .boardTitle("제목을 입력해주세요")
                .boardSubTitle("Hi welcome/안녕 어서와")
                .beforeLang("영어")
                .afterLang("한국어")
                .boardDescription("작품에 대한 설명")
                .highCtg("대분류")
                .lowCtg("하위카테고리")
                .boardAuthor("작가이름")
                .boardContent("Hello, my name is Hong Gil-dong and I live in Seoul./안녕하세요 저는 서울에 살고있는 홍길동 이라고 합니다$Hello, my name is Hong Gil-dong and I live in Seoul./안녕하세요 저는 서울에 살고있는 홍길동 이라고 합니다")
                .tempStorageAt(false)
                .fontSize(13)
                .fontType("Noto Sanz")
                .build();


        this.mockMvc.perform(post("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(board)))
                .andExpect(status().isOk())
                .andDo(document(
                        "/board",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디"),
                                fieldWithPath("boardTitle").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("boardSubTitle").type(JsonFieldType.STRING).description("부제목"),
                                fieldWithPath("beforeLang").type(JsonFieldType.STRING).description("번역전 언어"),
                                fieldWithPath("afterLang").type(JsonFieldType.STRING).description("번역후 언어"),
                                fieldWithPath("boardDescription").type(JsonFieldType.STRING).description("작품 설명"),
                                fieldWithPath("highCtg").type(JsonFieldType.STRING).description("대분류"),
                                fieldWithPath("lowCtg").type(JsonFieldType.STRING).description("하위카테고리"),
                                fieldWithPath("boardAuthor").type(JsonFieldType.STRING).description("작가"),
                                fieldWithPath("fontSize").type(JsonFieldType.NUMBER).description("글씨 크기"),
                                fieldWithPath("fontType").type(JsonFieldType.STRING).description("글꼴"),
                                fieldWithPath("boardContent").type(JsonFieldType.STRING).description("본문"),
                                fieldWithPath("tempStorageAt").type(JsonFieldType.BOOLEAN).description("임시저장 여부(false: 저장, true: 임시저장)")
                        )));

    }
}
