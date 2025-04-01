package com.example.transfolio.domain.career.repository;

import com.example.transfolio.domain.career.entity.CareerEntity;
import com.example.transfolio.domain.career.model.CareerDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CareerRepository extends JpaRepository<CareerEntity, Long> {

    @Query(value = """
    SELECT new com.example.transfolio.domain.career.model.CareerDto(
        c.careerPid
        , c.careerTitle
        , c.careerContent
        , c.careerDate
        , c.updatedAt
        , c.createdAt
        , c.userId
    ) FROM CareerEntity c WHERE c.userId = :userId
    """)
    List<CareerDto> getCareerListById(@Param("userId") String userId);

    int countByUserId(String userId);
}
