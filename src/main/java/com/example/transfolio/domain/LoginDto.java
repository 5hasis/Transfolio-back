package com.example.transfolio.domain;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String userId;

    private String password;
}
