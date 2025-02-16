package com.example.transfolio.domain.board.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardFoldResponseDto {

    private Long boardPid;
    private LocalDateTime createdAt;
    private String userId;

    private String boardTitle;
    private String boardSubTitle;
    private int foldCnt;
}
