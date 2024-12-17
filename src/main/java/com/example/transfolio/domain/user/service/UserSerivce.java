package com.example.transfolio.domain.user.service;

import com.example.transfolio.domain.user.entity.User;
import com.example.transfolio.domain.user.entity.UserIntrs;
import com.example.transfolio.domain.user.model.UserDto;
import com.example.transfolio.domain.user.model.UserIntrsDto;
import com.example.transfolio.domain.user.repository.UserIntrsRepository;
import com.example.transfolio.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class UserSerivce {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserIntrsRepository userIntrsRepository;

    /**
     * 회원가입
     */
    @Transactional
    public String registerUser(UserDto userDto) {

        if (!userRepository.findByUserId(userDto.getUserId()).isEmpty()) {
            return "존재하는 아이디";
        }

        User build = new User()
                .builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        Long userPid = userRepository.save(build).getUserPid();

        UserIntrs userIntrs = new UserIntrs()
                .builder()
                .userPid(userPid)
                .intrsLanguage(userDto.getUserIntrsDto().getIntrsLanguage())
                .intrsMajor(userDto.getUserIntrsDto().getIntrsMajor())
                .intrsCorporation(userDto.getUserIntrsDto().getIntrsCorporation())
                .intrsLiterature(userDto.getUserIntrsDto().getIntrsLiterature())
                .build();

        userIntrsRepository.save(userIntrs);

        return "success";

    }


}
