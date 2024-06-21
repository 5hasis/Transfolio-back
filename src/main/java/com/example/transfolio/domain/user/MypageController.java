package com.example.transfolio.domain.user;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.user.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private BoardService boardService;

    /**
     * 마이페이지 조회
     */
    @PostMapping("/")
    public List<BoardDto> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = null;
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                loginId = (String)principal;
            }
            if (loginId != null && !loginId.equals("")) {

                boardDtoList = boardService.getBoardListById(loginId);
            }
        }else {
            throw new IllegalStateException("Unexpected authentication principal type: " + authentication.getPrincipal().getClass());
        }

        return boardDtoList;
    }
}
