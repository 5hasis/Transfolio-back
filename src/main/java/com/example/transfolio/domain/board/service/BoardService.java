package com.example.transfolio.domain.board.service;

import com.example.transfolio.domain.board.entity.Board;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.repository.BoardRepository;
import com.example.transfolio.domain.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    public List<BoardDto> getBoardListById(UserDto userDto){
        List<Board> boardList = boardRepository.findByUserId(userDto.getUserId());

        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board : boardList){
            BoardDto boardDto = BoardDto.builder()
                    .boardPid(board.getBoardPid())
                    .boardTitle(board.getBoardTitle())
                    .boardAfterLang(board.getBoardAfterLang())
                    .boardBeforeLang(board.getBoardBeforeLang())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }
}
