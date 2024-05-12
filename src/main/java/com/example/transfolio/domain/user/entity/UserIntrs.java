package com.example.transfolio.domain.user.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@Table(name = "tr_member_intrs")
public class UserIntrs {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_INTRS_PID")
    private Long pid;

    @NotNull
    private String userId;

    @NonNull
    private String intrsLanguage;

    @NonNull
    private String intrsMajor;

    @NonNull
    private String intrsLiterature;

    @NonNull
    private String intrsCorporation;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserIntrs() {

    }
}
