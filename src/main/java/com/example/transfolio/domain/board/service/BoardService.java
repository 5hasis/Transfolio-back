package com.example.transfolio.domain.board.service;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.entity.BoardFoldHistEntity;
import com.example.transfolio.domain.board.model.BoardDto;
import com.example.transfolio.domain.board.model.BoardFoldHistDto;
import com.example.transfolio.domain.board.repository.BoardFoldHistRepository;
import com.example.transfolio.domain.board.repository.BoardRepository;
import com.example.transfolio.security.AuthenticationUtil;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFoldHistRepository boardFoldHistRepository;

    public BoardService(BoardRepository boardRepository, BoardFoldHistRepository boardFoldHistRepository) {
        this.boardRepository = boardRepository;
        this.boardFoldHistRepository = boardFoldHistRepository;
    }

    /* 게시글 저장 */
    public ResObj registerBoard(BoardDto board) {

        BoardEntity boardEntity = new BoardEntity(board);
        BoardEntity save = boardRepository.save(boardEntity);

        return new ResObj(save);
    }

    /* 아이디로 게시물 조회 */
    public List<BoardDto> getBoardListById(String userId) {

        String loginId = AuthenticationUtil.getLoginIdFromAuthentication();
        String isSelf = "";

        //본인이 아니면 임시저장된 글은 제외하고 조회
        if(loginId != null && !loginId.equals("") && loginId.equals(userId)){
            isSelf = "Y";
        }
        else{
            isSelf = "N";
        }
        List<BoardEntity> boardList = boardRepository.findByUserUserIdNative(userId, isSelf);

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

    /* 게시물 찜하기 저장 및 취소(삭제)*/
    @Transactional
    public JSONObject saveBookmark(BoardFoldHistDto boardFoldHistDto){

        int result = 0;
        BoardFoldHistEntity boardFoldHistEntity = new BoardFoldHistEntity(boardFoldHistDto);

        String boardPid = boardFoldHistDto.getBoardPid();
        String userId = boardFoldHistDto.getUserId();

        Optional<BoardFoldHistEntity> existingHist = boardFoldHistRepository.findByBoardPidAndUserId(boardPid, userId);

        //기존 찜하기한 이력이 있으면 찜 취소(이력 삭제)
        if (existingHist.isPresent()) {
            boardFoldHistRepository.delete(existingHist.get());

        }else{
            result = boardRepository.addBoardFoldCnt(String.valueOf(boardFoldHistDto.getBoardPid()));

            boardFoldHistRepository.save(boardFoldHistEntity);
        }


        return new ResObj(result).getObject();
    }

}
