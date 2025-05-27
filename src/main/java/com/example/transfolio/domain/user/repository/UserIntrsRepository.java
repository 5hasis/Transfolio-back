package com.example.transfolio.domain.user.repository;


import com.example.transfolio.domain.user.entity.UserEntity;
import com.example.transfolio.domain.user.entity.UserIntrsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserIntrsRepository extends JpaRepository<UserIntrsEntity, String> {

    Optional<UserIntrsEntity> findByUser(UserEntity user);

}
