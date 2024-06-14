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
    private String boardTitle;
    private String boardAfterLang;
    private String boardBeforeLang;
    private User user;

    /* DTO -> Entity */
    public Board toEntity(){
        Board board = Board.builder()
                .boardPid(boardPid)
                .boardTitle(boardTitle)
                .boardAfterLang(boardAfterLang)
                .boardBeforeLang(boardBeforeLang)
                .user(user)
                .build();

        return board;
    }

}
