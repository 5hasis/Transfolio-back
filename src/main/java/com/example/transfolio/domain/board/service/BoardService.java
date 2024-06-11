package com.example.transfolio.domain.board.service;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    /* 게시글 저장 */
    public ResObj registerBoard(BoardDto board) {

        BoardEntity boardEntity = new BoardEntity(board);
        BoardEntity save = boardRepository.save(boardEntity);

        return new ResObj(save);
    }
}
