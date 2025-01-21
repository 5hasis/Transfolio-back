package com.example.transfolio.domain.career.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "tr_career")
public class CareerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerPid;

    private String careerTitle;

    private String careerContent;

    private String careerDate;

    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String userId;

    public CareerEntity() {

    }
}
