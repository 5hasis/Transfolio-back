package com.example.transfolio.domain.user.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@Table(name = "tr_member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPid;

    @NonNull
    private String userId;

    @NonNull
    private String password;

    @NonNull
    private String email;

    @OneToOne
    @JoinColumn(name = "user_pid")
    private UserIntrs userIntrs;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User() {

    }
}
