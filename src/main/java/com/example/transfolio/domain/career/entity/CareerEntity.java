package com.example.transfolio.domain.career.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @LastModifiedDate // 업데이트 시 자동 갱신
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(updatable = false) // 수정 방지
    private LocalDateTime createdAt;

    private String userId;

    public CareerEntity() {

    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();  // updatedAt 값 설정
    }
}
