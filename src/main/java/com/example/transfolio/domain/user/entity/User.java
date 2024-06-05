package com.example.transfolio.domain.user.entity;

import com.example.transfolio.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<Board> boardList;
}
