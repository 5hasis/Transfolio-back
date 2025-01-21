package com.example.transfolio.domain.user.model;

import com.example.transfolio.domain.board.model.BoardDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoResponseDto {

    private UserInfoDto userInfoDto;

    @JsonProperty("isAuthorYn") // JSON 필드명을 강제로 지정
    private boolean isAuthorYn;
}
