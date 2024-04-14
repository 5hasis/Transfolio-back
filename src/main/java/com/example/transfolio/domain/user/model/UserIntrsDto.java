package com.example.transfolio.domain.user.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserIntrsDto {


    private Long userPid;

    @NonNull
    private String intrsLanguage;

    @NonNull
    private String intrsMajor;

    @NonNull
    private String intrsLiterature;

    @NonNull
    private String intrsCorporation;

}
