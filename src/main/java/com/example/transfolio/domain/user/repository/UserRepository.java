package com.example.transfolio.domain.user.repository;

import com.example.transfolio.domain.user.entity.UserEntity;
import com.example.transfolio.domain.user.model.UserInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findByUserId(String userId);

    @Query("""
            SELECT new com.example.transfolio.domain.user.model.UserInfoDto(
                u.userId
                , u.email
                , ui.intrsCorporation
                , ui.intrsLanguage
                , ui.intrsMajor
                , ui.intrsLiterature
                , COALESCE(SUM(b.foldCnt), 0)
                , u.userDscr
            )
            FROM UserEntity u 
            LEFT JOIN u.boardList b
            LEFT JOIN u.userIntrs ui
            WHERE u.userId = :userId
            GROUP BY u.userId, u.email, ui
            """)
    UserInfoDto findInfoByUserId(@Param("userId") String userId);

}
