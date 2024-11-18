package com.example.transfolio.domain.career.service;

import com.example.transfolio.domain.career.entity.CareerEntity;
import com.example.transfolio.domain.career.model.CareerDto;
import com.example.transfolio.domain.career.repository.CareerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    private final CareerRepository careerRepository;

    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    public List<CareerDto> getCareerListById(String userId) {
        return careerRepository.getCareerListById(userId);
    }


    public CareerEntity saveCareer(CareerEntity careerEntity) {
        return careerRepository.save(careerEntity);
    }

    // 경력 삭제
    public void deleteCareer(Long careerPid, String loginId) {
        // 경력 조회
        CareerEntity careerEntity = careerRepository.findById(careerPid)
                .orElseThrow(() -> new RuntimeException("경력을 찾을 수 없습니다."));

        // 작성자와 일치하는지 확인
        if (!careerEntity.getUserId().equals(loginId)) {
            throw new RuntimeException("삭제할 권한이 없습니다.");
        }

        // 경력 삭제
        careerRepository.delete(careerEntity);
    }
}
