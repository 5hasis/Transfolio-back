package com.example.transfolio.domain.board.model;

import com.example.transfolio.domain.board.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;


import lombok.*;

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

    private boolean tempStorageAt;

    private int fontSize;

    private String fontType;

    private String foldCnt;

    private String tempStorageYn;

    //private String userId;

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
