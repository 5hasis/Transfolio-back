package com.example.transfolio.domain.career.model;

import lombok.*;

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

    private String createdAt;

    private String userId;

    public CareerDto(String careerTitle, String careerContent, String careerDate) {
        this.careerTitle = careerTitle;
        this.careerContent = careerContent;
        this.careerDate = careerDate;
    }

}
