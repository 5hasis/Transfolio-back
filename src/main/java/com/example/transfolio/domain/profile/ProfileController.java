package com.example.transfolio.domain.profile;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.user.entity.UserEntity;
import com.example.transfolio.domain.user.model.UserInfoDto;
import com.example.transfolio.domain.user.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private BoardService boardService;

    /**
     * 프로필 포트폴리오 탭 조회
     */
    @PostMapping("/portfolio")
    public List<BoardDto> getMyPortfolio(@RequestParam String userId) {

        List<BoardDto> boardDtoList = boardService.getBoardListById(userId);

        return boardDtoList;
    }

    /**
     * 프로필 경력 탭 조회
     */
    @PostMapping("/career")
    public List<BoardDto> getMyCareer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = null;
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                loginId = (String)principal;
            }
            if (loginId != null && !loginId.equals("")) {

                //boardDtoList = boardService.getBoardListById(loginId);
            }
        }else {
            throw new IllegalStateException("Unexpected authentication principal type: " + authentication.getPrincipal().getClass());
        }

        return boardDtoList;
    }

    /**
     * 프로필 사용자 정보
     */
    @PostMapping("/myInfo")
    public UserInfoDto getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = null;

        UserInfoDto userinfoDto = new UserInfoDto();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                loginId = (String)principal;
            }
            if (loginId != null && !loginId.equals("")) {
                UserEntity user = userSerivce.getUserByUserId(loginId);
                userinfoDto.setUserId(user.getUserId());
                userinfoDto.setUserIntrs(user.getUserIntrs());
            }
        }else {
            throw new IllegalStateException("Unexpected authentication principal type: " + authentication.getPrincipal().getClass());
        }

        return userinfoDto;
    }

}
