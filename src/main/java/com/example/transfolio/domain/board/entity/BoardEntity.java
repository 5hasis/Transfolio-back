package com.example.transfolio.domain.board.entity;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@Table(name = "tr_board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardPid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String boardTitle;

    private String boardSubTitle;

    private String beforeLang;

    private String afterLang;

    private String boardDescription;

    private String highCtg;

    private String lowCtg;

    private String boardAuthor;

    private String boardContent;

    private boolean tempStorageAt;

    private int fontSize;

    private String fontType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public BoardEntity(BoardDto boardDto) {
        this.user = boardDto.getUser();
        this.boardTitle = boardDto.getBoardTitle();
        this.boardSubTitle = boardDto.getBoardSubTitle();
        this.beforeLang = boardDto.getBeforeLang();
        this.afterLang = boardDto.getAfterLang();
        this.boardDescription = boardDto.getBoardDescription();
        this.highCtg = boardDto.getHighCtg();
        this.lowCtg = boardDto.getLowCtg();
        this.boardAuthor = boardDto.getBoardAuthor();
        this.boardContent = boardDto.getBoardContent();
        this.tempStorageAt = boardDto.isTempStorageAt();
        this.fontSize = boardDto.getFontSize();
        this.fontType = boardDto.getFontType();
    }

    public BoardEntity() {

    }
}
