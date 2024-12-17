package com.example.transfolio.domain.profile;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.career.model.CareerDto;
import com.example.transfolio.domain.user.entity.UserEntity;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserInfoDto;
import com.example.transfolio.domain.user.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public List<BoardDto> getUserPortfolio(@RequestBody UserDto userDto) {

        List<BoardDto> boardDtoList = boardService.getBoardListById(userDto.getUserId());

        return boardDtoList;
    }

    /**
     * 프로필 경력 탭 조회
     */
    @PostMapping("/career")
    public List<CareerDto> getUserCareer(@RequestBody UserDto userDto) throws Exception {

        String userId = userDto.getUserId();
        List<CareerDto> CareerDtoList = new ArrayList<>();

        if (userId != null && !userId.equals("")) {
            //CareerDtoList = careerService.getCareerListById(userId);
         }else {
            throw new Exception();
        }

        return CareerDtoList;
    }

    /**
     * 프로필 사용자 정보
     */
    @PostMapping("/myInfo")
    public UserInfoDto getUserInfo(@RequestBody UserDto userDto) throws Exception {

        String userId = userDto.getUserId();
        UserInfoDto userinfoDto = new UserInfoDto();

        if (userId != null && !userId.equals("")) {
            UserEntity user = userSerivce.getUserByUserId(userId);
            userinfoDto.setUserId(user.getUserId());
            userinfoDto.setUserIntrs(user.getUserIntrs());
        }else {
            throw new Exception();
        }

        //접기 숫자 하드코딩
        userinfoDto.setFoldCnt("708");
        return userinfoDto;
    }

}
