package com.example.transfolio.domain;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/homeIntrs")
    public List<BoardDto> homeIntrs() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = null;
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                loginId = (String)principal;
            }
            if (loginId != null && !loginId.equals("")) {

                boardDtoList = boardService.getHomeIntrsBoard(loginId);
            }
        }else {
            throw new IllegalStateException("Unexpected authentication principal type: " + authentication.getPrincipal().getClass());
        }

        return boardDtoList;
    }

    @GetMapping("/todaysTranslator")
    public ResponseEntity<List<BoardDto>> getTopBoards() {
        List<BoardDto> topBoards = boardService.getTop9Boards();
        return ResponseEntity.ok(topBoards);
    }
}
