package com.example.transfolio.domain.board.repository;

import com.example.transfolio.domain.board.entity.BoardFoldHistEntity;
import com.example.transfolio.domain.board.model.BoardFoldHistDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardFoldHistRepository extends JpaRepository<BoardFoldHistEntity, String> {

    // boardPid와 userId로 기록을 찾는 메서드
    Optional<BoardFoldHistEntity> findByBoardPidAndUserId(String boardPid, String userId);

    @Query(value = """
        SELECT new com.example.transfolio.domain.board.model.BoardFoldHistDto(
        f.foldHistPid
        , f.boardPid
        , f.updatedAt
        , f.createdAt
        , f.userId
        ) FROM BoardFoldHistEntity f WHERE f.userId = :userId
    """)
    List<BoardFoldHistDto> getBookmarkListById(@Param("userId") String userId);
}
