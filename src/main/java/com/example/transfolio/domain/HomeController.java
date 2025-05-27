package com.example.transfolio.domain;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.user.model.UserInfoDto;
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

    @GetMapping("/translators/recommendations")
    public ResponseEntity<List<UserInfoDto>> getRecommendedTranslators(@RequestParam String category) {
        List<UserInfoDto> result = boardService.getRecommendedTranslatorsByCtg(category);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(result); // 200 OK
    }
}
