package com.example.transfolio.domain.user.model;

import com.example.transfolio.domain.user.entity.UserIntrs;
import lombok.*;

import javax.persistence.OneToOne;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NonNull
    private String userId;

    @NonNull
    private String password;

    @NonNull
    private String email;

    private UserIntrsDto userIntrsDto;

}
