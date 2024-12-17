package com.example.transfolio.domain.user.repository;

import com.example.transfolio.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findByUserId(String userId);

}
