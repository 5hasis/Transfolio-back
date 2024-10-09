package com.example.transfolio.domain.board.repository;

import com.example.transfolio.domain.board.entity.BoardFoldHistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFoldHistRepository extends JpaRepository<BoardFoldHistEntity, String> {
}
