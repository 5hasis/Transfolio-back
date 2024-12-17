package com.example.transfolio.domain.board.model;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardRegistDto {

    @NonNull
    private String userId;

    private String boardTitle;

    private String boardSubTitle;

    private String beforeLang;

    private String afterLang;

    private String boardDescription;

    private String highCtg;

    private String lowCtg;

    private String boardAuthor;

    private String boardContent;

    private boolean tempStorageAt;

    private int fontSize;

    private String fontType;
}
