package com.example.transfolio.domain.career.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CareerDto {


    private String careerTitle;

    private String careerContent;

    private String careerDate;

    private String updatedAt;

    private LocalDateTime createdAt;

    private String userId;


}
