package com.example.transfolio.domain.user.model;

import com.example.transfolio.domain.user.entity.UserIntrsEntity;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    @NonNull
    private String userId;

    private String email;

    private String intrsCorporation;

    private String intrsLiterature;

    private String intrsMajor;

    private String intrsLanguage;

    private long totalFoldCnt;

    public UserInfoDto(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

}
