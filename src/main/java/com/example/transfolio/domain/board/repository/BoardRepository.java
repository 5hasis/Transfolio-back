package com.example.transfolio.domain.board.repository;

import com.example.transfolio.domain.board.entity.BoardEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    @Query(value = "SELECT * FROM tr_board WHERE user_id = :userId", nativeQuery = true)
    List<BoardEntity> findByUserUserIdNative(@Param("userId") String userId);

    List<BoardEntity> findAllByOrderByCreatedAtDesc();

}
