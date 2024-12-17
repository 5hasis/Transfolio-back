package com.example.transfolio.domain.user.model;

import com.example.transfolio.domain.user.entity.UserIntrs;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    @NonNull
    private String userId;

    private String email;

    private UserIntrs userIntrs;

}
