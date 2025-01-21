package com.example.transfolio.domain.board.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardResponseDto {

    private BoardDto boardDto;

    @JsonProperty("isAuthorYn") // JSON 필드명을 강제로 지정
    private boolean isAuthorYn;

}
