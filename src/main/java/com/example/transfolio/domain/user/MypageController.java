package com.example.transfolio.domain.user;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getMyInfo(@RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDto) {
                userDto = (UserDto) principal;

                List<BoardDto> BoardDtoList = boardService.getBoardListById(userDto);

                return "Welcome to your MyPage, " + userDto.getUserId() + "!";
            } else {
                return "Welcome to your MyPage!";
            }
        }

        return "Authentication required to access MyPage.";
    }
}
