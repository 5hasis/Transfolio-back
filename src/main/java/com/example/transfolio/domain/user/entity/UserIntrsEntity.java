package com.example.transfolio.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@Table(name = "tr_member_intrs")
@EntityListeners(AuditingEntityListener.class)
public class UserIntrsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_intrs_pid")
    private Long pid;

//    @NotNull
//    private String userId;
    @OneToOne
    @JoinColumn(name = "user_pid")
    @JsonBackReference
    private UserEntity user;


    @NonNull
    private String intrsLanguage;

    @NonNull
    private String intrsMajor;

    @NonNull
    private String intrsLiterature;

    @NonNull
    private String intrsCorporation;

    @CreatedDate
    @Column(updatable = false) // 수정 방지
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public UserIntrsEntity() {

    }
}
