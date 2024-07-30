package com.example.transfolio.domain.user.entity;

import com.example.transfolio.domain.board.entity.BoardEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = "userIntrs")
@EqualsAndHashCode(exclude = "userIntrs")
@AllArgsConstructor
@Table(name = "tr_member")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pid")
    private Long userPid;

    @NonNull
    @Column(name = "user_id", unique = true)
    private String userId;

    @NonNull
    private String password;

    @NonNull
    private String email;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private UserIntrs userIntrs;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User() {

    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<BoardEntity> boardList;
}
