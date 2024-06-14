package com.example.transfolio.domain.board.model;

import com.example.transfolio.domain.board.entity.Board;
import com.example.transfolio.domain.user.entity.User;
import lombok.*;

@Builder
@Data
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

    private User user;

    /* DTO -> Entity */
    public Board toEntity(){
        Board board = Board.builder()
                .boardPid(boardPid)
                .boardTitle(boardTitle)
                .boardAfterLang(afterLang)
                .boardBeforeLang(beforeLang)
                .user(user)
                .build();

        return board;
    }

}
