package com.example.transfolio.domain.board.entity;

import com.example.transfolio.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@Table(name = "tr_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardPid;



    @NonNull
    private String boardTitle; //제목

    private String boardBeforeLang; //번역전언어

    private String boardAfterLang; //번역후언어

    private String BoardDescription; //설명

    private String BoardHigtCtg; //대분류

    private String BoardSubCtg; //하위카테고리

    private String BoardFontSize; //글자크기

    private String field; //서체설정

    private String BoardLikeCnt; //찜하기

    private String RegDt; //등록일

    private String RegUserId; //등록자

    private String UpdateDt; //수정일

    private String UpdateUserId; //수정자

    public Board() {

    }
}
