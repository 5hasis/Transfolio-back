package com.example.transfolio.domain.board.repository;

import com.example.transfolio.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, String> {

    List<Board> findByUserId(String userId);
}
