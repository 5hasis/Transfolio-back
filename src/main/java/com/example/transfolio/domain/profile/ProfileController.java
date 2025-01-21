package com.example.transfolio.domain.profile;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.service.BoardService;
import com.example.transfolio.domain.career.model.CareerDto;
import com.example.transfolio.domain.career.service.CareerService;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserInfoDto;
import com.example.transfolio.domain.user.model.UserInfoResponseDto;
import com.example.transfolio.domain.user.service.UserSerivce;
import com.example.transfolio.security.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    private CareerService careerService;

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
            CareerDtoList = careerService.getCareerListById(userId);
         }else {
            throw new Exception();
        }

        return CareerDtoList;
    }

    /**
     * 프로필 사용자 정보
     */
    @PostMapping("/myInfo")
    public UserInfoResponseDto getUserInfo(@RequestBody UserDto userDto) throws Exception {

        String loginId = AuthenticationUtil.getLoginIdFromAuthentication();

        String userId = userDto.getUserId();
        UserInfoDto userInfoDto = new UserInfoDto();

        boolean isAuthorYn;

        if (userId != null && !userId.equals("")) {
            userInfoDto = userSerivce.getUserByUserId(userId);

            // 작성자 여부 판단
            isAuthorYn = userId.equals(loginId);


        }else {
            throw new Exception();
        }

        return new UserInfoResponseDto(userInfoDto, isAuthorYn);
    }

}
