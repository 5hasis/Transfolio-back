package com.example.transfolio.domain.board.repository;

import com.example.transfolio.domain.board.entity.BoardEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    @Query(value = "SELECT * FROM tr_board WHERE user_id = :userId AND (:isSelf = 'Y' OR (temp_storage_yn = 'N' AND :isSelf = 'N'))", nativeQuery = true)
    List<BoardEntity> findByUserUserIdNative(@Param("userId") String userId, @Param("isSelf") String isSelf);

    @Query(value = "SELECT * FROM tr_board WHERE user_id = :userId", nativeQuery = true)
    List<BoardEntity> findByOrderByCreatedAtDesc(@Param("userId") String userId);

    //찜하기 등록
    // board 테이블 fold_cnt+1
    @Modifying
    @Query(value = "UPDATE tr_board SET fold_cnt = fold_cnt + 1 WHERE board_pid = :boardPid", nativeQuery = true)
    int addBoardFoldCnt(@Param("boardPid") String boardPid);



}
