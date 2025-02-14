package com.example.transfolio.domain.board.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardFoldHistDto {

    private long foldHistPid;
    private String boardPid;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String userId;
}
