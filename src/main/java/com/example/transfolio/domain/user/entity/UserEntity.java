package com.example.transfolio.domain.user.entity;

import com.example.transfolio.domain.board.entity.BoardEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements Serializable {

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

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private UserIntrsEntity userIntrs;

    @CreatedDate
    @Column(updatable = false) // 수정 방지
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String userDscr;

    private String nickname;

    public UserEntity() {

    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<BoardEntity> boardList;
}
