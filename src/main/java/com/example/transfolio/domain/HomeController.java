package com.example.transfolio.domain;

import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public List<BoardEntity> initialHome() {
        return boardService.getHomeBoard(); // 초기 데이터는 첫 페이지 9개
    }
}
