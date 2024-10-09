package com.example.transfolio.domain.board;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.model.BoardFoldHistDto;
import com.example.transfolio.domain.board.service.BoardService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("board")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping()
    public ResObj createBoard(@RequestBody BoardDto board, @RequestHeader HttpHeaders httpHeaders) {


        return boardService.registerBoard(board);
    }

    @PostMapping("/bookmark")
    public JSONObject saveBookmark(@RequestBody BoardFoldHistDto foldHistDto){
        JSONObject jsonObject = boardService.saveBookmark(foldHistDto);
        return jsonObject;
    }





}
