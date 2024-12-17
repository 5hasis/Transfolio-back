package com.example.transfolio.domain.board.service;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /* 게시글 저장 */
    public ResObj registerBoard(BoardDto board) {

        BoardEntity boardEntity = new BoardEntity(board);
        BoardEntity save = boardRepository.save(boardEntity);

        return new ResObj(save);
    }

    /* 아이디로 게시물 조회 */
    public List<BoardDto> getBoardListById(String userId) {
        List<BoardEntity> boardList = boardRepository.findByUserUserIdNative(userId);

        List<BoardDto> boardDtoList = new ArrayList<>();
        for (BoardEntity board : boardList) {
            BoardDto boardDto = BoardDto.builder()
                    .boardPid(board.getBoardPid())
                    .userId(board.getUserId())
                    .boardTitle(board.getBoardTitle())
                    .boardDescription(board.getBoardDescription())
                    .afterLang(board.getAfterLang())
                    .beforeLang(board.getBeforeLang())
                    .foldCnt(board.getFoldCnt())
                    .tempStorageYn(board.getTempStorageYn())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    /* 홈화면 게시물 조회(관심분야 9개) */
    public List<BoardDto> getHomeIntrsBoard(String userId) {
        List<BoardEntity> boardList = boardRepository.findByOrderByCreatedAtDesc(userId);

        List<BoardDto> boardDtoList = new ArrayList<>();
        for (BoardEntity board : boardList) {
            BoardDto boardDto = BoardDto.builder()
                    .boardPid(board.getBoardPid())
                    .boardTitle(board.getBoardTitle())
                    .afterLang(board.getAfterLang())
                    .beforeLang(board.getBeforeLang())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

}
