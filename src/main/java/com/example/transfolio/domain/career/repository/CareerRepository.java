package com.example.transfolio.domain.career.repository;

import com.example.transfolio.domain.board.entity.BoardEntity;
import com.example.transfolio.domain.career.model.CareerDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CareerRepository extends JpaRepository<BoardEntity, String> {

    @Query(value = "SELECT new com.example.transfolio.domain.career.model.CareerDto(c.careerTitle, c.careerDate, c.careerContent) FROM CareerEntity c WHERE c.userId = :userId")
    List<CareerDto> getCareerListById(@Param("userId") String userId);
}
