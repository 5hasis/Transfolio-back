package com.example.transfolio.domain.board.repository;

import com.example.transfolio.domain.board.entity.BoardFoldHistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardFoldHistRepository extends JpaRepository<BoardFoldHistEntity, String> {

    // boardPid와 userId로 기록을 찾는 메서드
    Optional<BoardFoldHistEntity> findByBoardPidAndUserId(String boardPid, String userId);
}
