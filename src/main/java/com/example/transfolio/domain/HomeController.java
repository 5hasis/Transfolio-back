package com.example.transfolio.domain;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.model.BoardWithUserDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.user.model.UserInfoDto;
import com.example.transfolio.security.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/homeIntrs")
    public List<BoardWithUserDto> homeIntrs() {
        //token에서 로그인 아이디 가져와서 세팅
        String loginId = AuthenticationUtil.getLoginIdFromAuthentication();

        List<BoardWithUserDto> boardDtoList = boardService.getHomeIntrsBoard(loginId);

        return boardDtoList;
    }

    @GetMapping("/todaysTranslator")
    public ResponseEntity<List<BoardDto>> getTopBoards() {
        List<BoardDto> topBoards = boardService.getTop9Boards();
        return ResponseEntity.ok(topBoards);
    }

    @GetMapping("/translators/recommendations")
    public ResponseEntity<List<UserInfoDto>> getRecommendedTranslators(@RequestParam String category) {
        List<UserInfoDto> result = boardService.getRecommendedTranslatorsByCtg(category);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(result); // 200 OK
    }
}
