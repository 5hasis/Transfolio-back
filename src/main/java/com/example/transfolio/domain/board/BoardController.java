package com.example.transfolio.domain.board;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.model.BoardFoldHistDto;
import com.example.transfolio.domain.board.model.BoardRegistDto;
import com.example.transfolio.domain.board.model.BoardResponseDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.user.model.UserSummaryDto;
import com.example.transfolio.security.AuthenticationUtil;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("board")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/regist")
    public ResObj createBoard(@RequestBody BoardRegistDto board, @RequestHeader HttpHeaders httpHeaders) {


        return boardService.registerBoard(board);
    }

    @PostMapping("/bookmark")
    public JSONObject saveBookmark(@RequestBody BoardFoldHistDto foldHistDto){
        //token에서 로그인 아이디 가져와서 세팅
        String loginId = AuthenticationUtil.getLoginIdFromAuthentication();
        foldHistDto.setUserId(loginId);

        JSONObject jsonObject = boardService.saveBookmark(foldHistDto);
        return jsonObject;
    }

    @GetMapping("/{boardPid}")
    public ResponseEntity<BoardResponseDto> getBoardDetails(@PathVariable Long boardPid) {
        String loginId = AuthenticationUtil.getLoginIdFromAuthentication();
        BoardResponseDto responseDto = boardService.getBoardDetailsByBoardPid(boardPid, loginId);
        return ResponseEntity.ok(responseDto);
    }

    //제출 후 top3 번역가 조회
    @PostMapping("/top3-translators")
    public ResponseEntity<List<UserSummaryDto>> getTop3TranslatorByCtg(@RequestBody BoardDto boardDto){
        List<UserSummaryDto> top3List = boardService.getTop3TranslatorByCtg(boardDto);
        return ResponseEntity.ok(top3List);
    }



}
