package com.example.transfolio.domain.board.model;

import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardDto {

    private Long boardPid;

    private String userId;

    private String boardTitle;

    private String afterLang;

    private String beforeLang;

    private String boardSubTitle;

    private String boardDescription;

    private String highCtg;

    private String lowCtg;

    private String boardAuthor;

    private String boardContent;

    private int fontSize;

    private String fontType;

    private int foldCnt;

    private String tempStorageYn;

    private LocalDateTime updatedAt;

    /* DTO -> Entity */
    public BoardEntity toEntity(){
        BoardEntity board = BoardEntity.builder()
                .boardPid(boardPid)
                .boardTitle(boardTitle)
                .afterLang(afterLang)
                .beforeLang(beforeLang)
                .userId(userId)
                .build();

        return board;
    }

}
