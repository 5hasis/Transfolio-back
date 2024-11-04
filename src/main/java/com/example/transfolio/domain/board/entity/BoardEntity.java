package com.example.transfolio.domain.board.entity;

import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.model.BoardRegistDto;
import com.example.transfolio.domain.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@ToString(exclude = "user")
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "tr_board")
public class BoardEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardPid;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonManagedReference
    private UserEntity user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

    private String boardTitle;

    private String boardSubTitle;

    private String beforeLang;

    private String afterLang;

    private String boardDescription;

    private String highCtg;

    private String lowCtg;

    private String boardAuthor;

    private String boardContent;

    private int fontSize;

    private String fontType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int foldCnt;

    private String tempStorageYn;

    public BoardEntity(BoardDto boardDto) {
        this.userId = boardDto.getUserId();
        this.boardTitle = boardDto.getBoardTitle();
        this.boardSubTitle = boardDto.getBoardSubTitle();
        this.beforeLang = boardDto.getBeforeLang();
        this.afterLang = boardDto.getAfterLang();
        this.boardDescription = boardDto.getBoardDescription();
        this.highCtg = boardDto.getHighCtg();
        this.lowCtg = boardDto.getLowCtg();
        this.boardAuthor = boardDto.getBoardAuthor();
        this.boardContent = boardDto.getBoardContent();
        this.fontSize = boardDto.getFontSize();
        this.fontType = boardDto.getFontType();
        this.foldCnt = boardDto.getFoldCnt();
        this.tempStorageYn = boardDto.getTempStorageYn();
    }

    public BoardEntity(BoardRegistDto boardRegistDto) {
        this.userId = boardRegistDto.getUserId();
        this.boardTitle = boardRegistDto.getBoardTitle();
        this.boardSubTitle = boardRegistDto.getBoardSubTitle();
        this.beforeLang = boardRegistDto.getBeforeLang();
        this.afterLang = boardRegistDto.getAfterLang();
        this.boardDescription = boardRegistDto.getBoardDescription();
        this.highCtg = boardRegistDto.getHighCtg();
        this.lowCtg = boardRegistDto.getLowCtg();
        this.boardAuthor = boardRegistDto.getBoardAuthor();
        this.boardContent = boardRegistDto.getBoardContent();
        this.fontSize = boardRegistDto.getFontSize();
        this.fontType = boardRegistDto.getFontType();
        this.tempStorageYn = boardRegistDto.getTempStorageYn();
    }

    public BoardEntity() {

    }
}
