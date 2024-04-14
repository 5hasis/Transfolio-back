package com.example.transfolio.domain.user.repository;

import com.example.transfolio.domain.user.entity.User;
import com.example.transfolio.domain.user.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByUserId(String userId);

}
