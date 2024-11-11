package com.example.transfolio.board;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.common.utils.JwtUtil;
import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.model.BoardFoldHistDto;
import com.example.transfolio.domain.board.model.BoardRegistDto;
import com.example.transfolio.domain.board.repository.BoardRepository;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.user.model.UserSummaryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriHost = "3.36.105.195", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;


    @Test
    void registerBoard() throws Exception {

        BoardRegistDto board = BoardRegistDto.builder()
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
                .fontSize(13)
                .fontType("Noto Sanz")
                .tempStorageYn("N")
                .build();

        BoardEntity mockBoardEntity = new BoardEntity(board);

        ResObj mockResponse = new ResObj(mockBoardEntity);

        when(boardService.registerBoard(board)).thenReturn(mockResponse);

        this.mockMvc.perform(post("/board/regist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(board)))
                .andExpect(status().isOk())
                .andDo(document(
                        "board/regist",
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
                                fieldWithPath("tempStorageYn").type(JsonFieldType.STRING).description("임시저장여부(Y/N)")
                        )));

    }

    @Test
    @Transactional
    void saveBookmark() throws Exception {

        BoardFoldHistDto foldHistDto = BoardFoldHistDto.builder()
                .boardPid("1")
                .build();

        String userId = "accountTest";
        String token = JwtUtil.createToken(userId,"my-secret-key-123123", 500000); // 테스트용 사용자 계정

        // Expected response
        JSONObject mockResponse = new JSONObject();
        mockResponse.put("status", "200");
        mockResponse.put("result", 1);
        mockResponse.put("message", "success");

        ObjectNode json = objectMapper.createObjectNode();
        json.put("boardPid", "1");

        System.out.println("~~~~~");
        System.out.println(mockResponse.toString());

        when(boardService.saveBookmark(any(BoardFoldHistDto.class))).thenReturn(mockResponse);

        this.mockMvc.perform(post("/board/bookmark")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andDo(document(
                        "board/bookmark",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedRequestFields(
                                fieldWithPath("boardPid").type(JsonFieldType.STRING).description("게시물 고유번호")
                                //, fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 아이디")
                        ),
                        responseFields( // 응답 필드 문서화 추가
                                fieldWithPath("result").type(JsonFieldType.NUMBER).description("결과 데이터"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태 코드")
                        )

                ));
    }

    @Test
    public void testGetBoardDetailsByBoardPid() throws Exception {
        Long boardPid = 1L;
        BoardDto mockBoardDto = BoardDto.builder()
                .boardPid(boardPid)
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
                .fontSize(13)
                .fontType("Noto Sanz")
                .tempStorageYn("N")
                .foldCnt(78)
                .build();

        when(boardService.getBoardDetailsByBoardPid(boardPid)).thenReturn(mockBoardDto);

        this.mockMvc.perform(get("/board/{boardPid}", boardPid)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(
                        "board/{boardPid}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("boardPid").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("boardAuthor").type(JsonFieldType.STRING).description("게시물 작성자"),
                                fieldWithPath("boardContent").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("boardDescription").type(JsonFieldType.STRING).description("게시물 설명"),
                                fieldWithPath("boardSubTitle").type(JsonFieldType.STRING).description("게시물 부제목"),
                                fieldWithPath("boardTitle").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("highCtg").optional().type(JsonFieldType.STRING).description("대분류"),
                                fieldWithPath("lowCtg").optional().type(JsonFieldType.STRING).description("하위 카테고리"),
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("사용자 ID"),
                                fieldWithPath("foldCnt").type(JsonFieldType.NUMBER).description("게시물 접기 수"),
                                fieldWithPath("afterLang").type(JsonFieldType.STRING).description("게시물 후원 언어"),
                                fieldWithPath("beforeLang").type(JsonFieldType.STRING).description("게시물 전언 언어"),
                                fieldWithPath("fontSize").type(JsonFieldType.NUMBER).description("글꼴 크기"),
                                fieldWithPath("fontType").type(JsonFieldType.STRING).description("글꼴 유형"),
                                fieldWithPath("tempStorageYn").type(JsonFieldType.STRING).description("임시 저장 여부 Y/N")
                        )
                ));
    }

    @Test
    void testGetTop3TranslatorsByCtg() throws Exception {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("highCtg", "대분류");
        requestBody.put("lowCtg", "하위카테고리");


        // Mock 데이터를 생성 (가정: 서비스가 반환할 예상 데이터)
        List<UserSummaryDto> mockUserListtt = Arrays.asList(
                new UserSummaryDto("accountTest1", "user1@example.com"),
                new UserSummaryDto("accountTest2", "user1@example.com"),
                new UserSummaryDto("accountTest3", "user1@example.com")
        );


        // 서비스 메서드의 반환값 설정
        when(boardService.getTop3TranslatorByCtg(any(BoardDto.class))).thenReturn(mockUserListtt);


        this.mockMvc.perform(post("/board/top3-translators")
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()) // 응답 본문 출력
                .andDo(document(
                        "board/top3-translators",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("highCtg").type(JsonFieldType.STRING).description("대분류"),
                                fieldWithPath("lowCtg").type(JsonFieldType.STRING).description("하위 카테고리")
                        ),
                        responseFields(
                                fieldWithPath("[].userId").type(JsonFieldType.STRING).description("번역가의 사용자 ID"),
                                fieldWithPath("[].email").type(JsonFieldType.STRING).description("번역가의 이메일")
                        )
                ));

    }
}
