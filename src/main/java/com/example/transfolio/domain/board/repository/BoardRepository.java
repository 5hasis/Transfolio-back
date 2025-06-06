package com.example.transfolio.domain.board.repository;

import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.user.model.UserInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Query(value = "SELECT * FROM tr_board WHERE user_id = :userId AND (:isSelf = 'Y' OR (temp_storage_yn = 'N' AND :isSelf = 'N'))", nativeQuery = true)
    List<BoardEntity> findByUserUserIdNative(@Param("userId") String userId, @Param("isSelf") String isSelf);

    @Query(value = "SELECT * FROM tr_board WHERE user_id = :userId", nativeQuery = true)
    List<BoardEntity> findByOrderByCreatedAtDesc(@Param("userId") String userId);

    //찜하기 등록
    // board 테이블 fold_cnt+1
    @Modifying
    @Query(value = "UPDATE tr_board SET fold_cnt = fold_cnt + 1 WHERE board_pid = :boardPid", nativeQuery = true)
    int addBoardFoldCnt(@Param("boardPid") String boardPid);

    //찜하기 취소
    // board 테이블 fold_cnt-1
    @Modifying
    @Query(value = "UPDATE tr_board SET fold_cnt = fold_cnt -1 WHERE board_pid = :boardPid", nativeQuery = true)
    int subtractBoardFoldCnt(@Param("boardPid") String boardPid);

    @Query(value = """
            SELECT 
                new com.example.transfolio.domain.board.model.BoardDto(
                    b.boardPid,
                    b.userId,
                    b.boardTitle,
                    b.afterLang,
                    b.beforeLang,
                    b.boardSubTitle,
                    b.boardDescription,
                    b.highCtg,
                    b.lowCtg,
                    b.boardAuthor,
                    b.boardContent,
                    b.fontSize,
                    b.fontType,
                    COALESCE(b.foldCnt, 0),
                    b.tempStorageYn,
                    b.updatedAt
                )
            FROM BoardEntity b
            ORDER BY b.foldCnt DESC, b.createdAt DESC
            """)
    List<BoardDto> findTop9ByOrderByFoldCntAndCreatedAtDesc(Pageable pageable);


    @Query("""
            SELECT new com.example.transfolio.domain.board.model.BoardDto
            (
                b.boardPid,
                b.user.userId,
                b.boardTitle,
                b.afterLang,
                b.beforeLang,
                b.boardSubTitle,
                b.boardDescription,
                b.highCtg,
                b.lowCtg,
                b.boardAuthor,
                b.boardContent,
                b.fontSize,
                b.fontType,
                b.foldCnt,
                b.tempStorageYn,
                b.updatedAt
                ) 
            FROM BoardEntity b WHERE b.boardPid = :boardPid
            """)
    Optional<BoardDto> findBoardByBoardPid(@Param("boardPid") Long boardPid);


    @Query(value = """
        SELECT m.user_id, m.email, b.fold_cnt
        FROM tr_board b
        JOIN tr_member m ON b.user_id = m.user_id 
        WHERE b.high_ctg = :#{#boardDto.highCtg} 
        AND b.low_ctg = :#{#boardDto.lowCtg} 
        GROUP BY m.user_id, m.email, b.fold_cnt
        ORDER BY b.fold_cnt DESC 
        LIMIT 3
        """, nativeQuery = true)
    List<Object[]> findTop3TranslatorByCtg(@Param("boardDto") BoardDto boardDto);

    int countByUserId(String userId);

    @Query("SELECT NEW com.example.transfolio.domain.user.model.UserInfoDto(u.userId, u.email) " +
            "FROM UserEntity u " +
            "JOIN u.boardList b " +
            "JOIN BoardFoldHistEntity l ON b.boardPid = l.boardPid " +
            "WHERE b.highCtg = :ctg " +
            "GROUP BY b.boardPid, u.userPid, u.userId " + // UserEntity의 userPid, userId를 GROUP BY에 추가
            "ORDER BY COUNT(l.foldHistPid) DESC")
    List<UserInfoDto> findRecommendedTranslatorsByCtg(@Param("ctg") String ctg, Pageable pageable);

}
