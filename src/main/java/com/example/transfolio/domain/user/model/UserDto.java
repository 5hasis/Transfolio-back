package com.example.transfolio.domain.user.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NonNull
    private String userId;

    //@NonNull
    private String password;

    private String email;

    private UserIntrsDto userIntrsDto;

}
