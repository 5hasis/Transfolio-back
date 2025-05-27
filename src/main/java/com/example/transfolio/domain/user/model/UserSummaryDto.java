package com.example.transfolio.domain.user.model;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {

    @NonNull
    private String userId;

    private String email;



}
