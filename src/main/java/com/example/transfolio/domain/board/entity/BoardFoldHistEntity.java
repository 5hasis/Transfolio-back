package com.example.transfolio.domain.board.entity;

import com.example.transfolio.domain.board.model.BoardFoldHistDto;
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
@Table(name = "tr_board_fold_hist")
public class BoardFoldHistEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foldHistPid;

    private String boardPid;

    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String userId;

    public BoardFoldHistEntity(BoardFoldHistDto boardfoldHistDto) {
        this.userId = boardfoldHistDto.getUserId();
        this.foldHistPid = boardfoldHistDto.getFoldHistPid();
        this.boardPid = boardfoldHistDto.getBoardPid();
        this.updatedAt = boardfoldHistDto.getUpdatedAt();
        //this.createdAt = boardfoldHistDto.getCreatedAt();

    }

    public BoardFoldHistEntity() {

    }
}
