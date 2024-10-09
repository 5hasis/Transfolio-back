package com.example.transfolio.domain.board.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardFoldHistDto {

    private long foldHistPid;
    private String boardPid;
    private String updatedAt;
    private String createdAt;
    private String userId;
}
