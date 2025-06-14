package com.example.transfolio.domain.board.service;

import com.example.transfolio.common.error.BusinessException;
import com.example.transfolio.common.error.ErrorEnum;
import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.board.entity.BoardFoldHistEntity;
import com.example.transfolio.domain.board.model.*;
import com.example.transfolio.domain.board.repository.BoardFoldHistRepository;
import com.example.transfolio.domain.board.repository.BoardRepository;
import com.example.transfolio.domain.user.entity.UserEntity;
import com.example.transfolio.domain.user.model.UserInfoDto;
import com.example.transfolio.domain.user.model.UserSummaryDto;
import com.example.transfolio.domain.user.repository.UserRepository;
import com.example.transfolio.security.AuthenticationUtil;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private ModelMapper modelMapper;  // ModelMapper 주입

    private final BoardRepository boardRepository;
    private final BoardFoldHistRepository boardFoldHistRepository;
    private final UserRepository userRepository;


    public BoardService(BoardRepository boardRepository, BoardFoldHistRepository boardFoldHistRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.boardFoldHistRepository = boardFoldHistRepository;
        this.userRepository = userRepository;
    }

    /* 게시글 저장 */
    public ResObj registerBoard(BoardRegistDto board) {

        List<UserEntity> userEntityList = userRepository.findByUserId(board.getUserId());
        if (userEntityList.isEmpty()) {
            throw new BusinessException(ErrorMessage.USER_NOT_FOUND); //
        }
        UserEntity userEntity = userEntityList.get(0);

        BoardEntity boardEntity = new BoardEntity(board);
        boardEntity.setUser(userEntity);

        BoardEntity save = boardRepository.save(boardEntity);

        return new ResObj(save);
    }

    /* 게시글 수정 */
    public ResObj updateBoard(Long boardPid, BoardRegistDto board, String loginId) throws Exception {

        // 기존 게시물 조회
        //new Entity 하면 새로운걸로 간주되어 createdAt 갱신됨
        BoardEntity existingBoardEntity = boardRepository.findById(boardPid)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));


        existingBoardEntity.setBoardPid(boardPid);

        if (!existingBoardEntity.getUserId().equals(loginId)) {
            throw new Exception("본인만 게시물을 수정할 수 있습니다.");
        }

        board.setUserId(loginId);

        // DTO의 값으로 기존 엔티티를 갱신
        modelMapper.map(board, existingBoardEntity); // DTO -> 엔티티로 한 번에 매핑

        BoardEntity save = boardRepository.save(existingBoardEntity);

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
    public List<BoardWithUserDto> getHomeIntrsBoard(String userId) {

        Pageable top9 = PageRequest.of(0, 9);
        List<BoardEntity> boardList = boardRepository.findTop9ByUserIntrsFetchJoin(userId, top9);

        return boardList.stream()
                .map(board -> BoardWithUserDto.builder()
                        .boardPid(board.getBoardPid())
                        .boardTitle(board.getBoardTitle())
                        .highCtg(board.getHighCtg())
                        .lowCtg(board.getLowCtg())
                        .foldCnt(board.getFoldCnt())
                        .nickname(board.getUser().getNickname()) // ← 여기는 N+1 아님! 이미 join되어 있음
                        .build())
                .collect(Collectors.toList());
    }

    /* 게시물 찜하기 저장 및 취소(삭제)*/
    @Transactional
    public JSONObject saveBookmark(BoardFoldHistDto boardFoldHistDto){

        int result = 0;
        BoardFoldHistEntity boardFoldHistEntity = new BoardFoldHistEntity(boardFoldHistDto);

        Long boardPid = boardFoldHistDto.getBoardPid();
        String userId = boardFoldHistDto.getUserId();

        Optional<BoardFoldHistEntity> existingHist = boardFoldHistRepository.findByBoardPidAndUserId(boardPid, userId);

        //기존 찜하기한 이력이 있으면 찜 취소(이력 삭제)
        if (existingHist.isPresent()) {
            boardFoldHistRepository.delete(existingHist.get());
            result = boardRepository.subtractBoardFoldCnt(String.valueOf(boardFoldHistDto.getBoardPid()));

        }else{
            result = boardRepository.addBoardFoldCnt(String.valueOf(boardFoldHistDto.getBoardPid()));

            boardFoldHistRepository.save(boardFoldHistEntity);
        }


        return new ResObj(result).getObject();
    }

    public List<BoardDto> getTop9Boards() {
        try {

            Pageable pageable = PageRequest.of(0, 9); // 0페이지의 9개 결과를 요청
            List<BoardDto> topBoards = boardRepository.findTop9ByOrderByFoldCntAndCreatedAtDesc(pageable);

            topBoards = topBoards.stream()
                    .distinct() // 중복 제거
                    .collect(Collectors.toList()); // 리스트로 변환

            return topBoards;
            // return topBoards.stream().distinct().limit(9).toList(); // 중복 제거 후 최대 9개로 제한
        } catch (Exception e) {
            // 예외 처리: 로그를 남기거나 다른 오류 응답을 보낼 수 있음
            System.err.println("Error occurred while fetching top boards: " + e.getMessage());
            // 예외 발생 시 빈 리스트 반환 또는 적절한 오류 처리
            return Collections.emptyList();
        }
    }

    public BoardResponseDto getBoardDetailsByBoardPid(Long boardPid, String loginId) {

        BoardDto boardDto = boardRepository.findBoardByBoardPid(boardPid)
                .orElseThrow(() -> new EntityNotFoundException //값이 없을 때
                        ("Board not found with pid: " + boardPid));

        // 작성자 여부 판단
        boolean isAuthorYn = boardDto.getUserId().equals(loginId);

        return new BoardResponseDto(boardDto, isAuthorYn);
    }

    public List<UserSummaryDto> getTop3TranslatorByCtg(BoardDto boardDto){

        try {

            List<Object[]> resultList = boardRepository.findTop3TranslatorByCtg(boardDto);


            List<UserSummaryDto> top3Translators = resultList.stream()
                    .map(result -> new UserSummaryDto((String) result[0], (String) result[1]))
                    .collect(Collectors.toList());

            return top3Translators;

        } catch (Exception e) {
            // 예외 처리: 로그를 남기거나 다른 오류 응답을 보낼 수 있음
            System.err.println("Error occurred while fetching top translator: " + e.getMessage());
            // 예외 발생 시 빈 리스트 반환 또는 적절한 오류 처리
            return Collections.emptyList();
        }
    }

    //게시물 삭제
    public void deleteBoard(Long boardPid, String loginId){
        //게시물 조회
        BoardEntity boardEntity = boardRepository.findById(boardPid)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        // 작성자와 일치하는지 확인
//        if (!boardEntity.getUserId().equals(loginId)) {
//            throw new RuntimeException("삭제할 권한이 없습니다.");
//        }

        //게시물 삭제
        boardRepository.delete(boardEntity);

    }

    //내정보(프로필) 북마크 리스트 조회
    public List<BoardFoldResponseDto> getBookmarkListById(String userId) {
        return boardFoldHistRepository.getBookmarkedBoardsByUserId(userId);
    }

    public int getBoardCountById(String userId) {
        return boardRepository.countByUserId(userId);
    }

    public int getBookmarkCountById(String userId){
        return boardFoldHistRepository.countByUserId(userId);
    }

    public List<UserInfoDto> getRecommendedTranslatorsByCtg(String category){
        Pageable pageable = PageRequest.of(0, 6); // 상위 6개 결과만 가져오도록 설정
        return boardRepository.findRecommendedTranslatorsByCtg(category, pageable);

    }
}
