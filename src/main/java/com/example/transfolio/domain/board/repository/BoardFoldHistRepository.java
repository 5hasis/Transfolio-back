package com.example.transfolio.domain.board.repository;

import com.example.transfolio.domain.board.entity.BoardFoldHistEntity;
import com.example.transfolio.domain.board.model.BoardFoldResponseDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardFoldHistRepository extends JpaRepository<BoardFoldHistEntity, String> {

    // boardPid와 userId로 기록을 찾는 메서드
    Optional<BoardFoldHistEntity> findByBoardPidAndUserId(Long boardPid, String userId);

    @Query("SELECT new com.example.transfolio.domain.board.model.BoardFoldResponseDto( " +
            "b.boardPid, f.createdAt, b.userId, " +
            "b.boardTitle, b.boardSubTitle, b.foldCnt) " +
            "FROM BoardFoldHistEntity f " +
            "JOIN BoardEntity b ON f.boardPid = b.boardPid " +
            "WHERE f.userId = :userId " +
            "ORDER BY f.createdAt DESC")
    List<BoardFoldResponseDto> getBookmarkedBoardsByUserId(@Param("userId") String userId);

    int countByUserId(String userId);
}
