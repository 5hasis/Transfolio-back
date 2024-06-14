package com.example.transfolio.domain.board.service;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.repository.BoardRepository;
import com.example.transfolio.domain.user.model.UserDto;
import lombok.RequiredArgsConstructor;
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

    public List<BoardDto> getBoardListById(UserDto userDto) {
        List<BoardEntity> boardList = boardRepository.findByUserId(userDto.getUserId());

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
