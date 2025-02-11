package com.example.transfolio.domain.career.service;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.career.entity.CareerEntity;
import com.example.transfolio.domain.career.model.CareerDto;
import com.example.transfolio.domain.career.repository.CareerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    @Autowired
    private ModelMapper modelMapper;  // ModelMapper 주입

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

    //경력 수정
    public ResObj updateCareer(CareerDto careerDto){
        // 기존 게시물 조회
        //new Entity 하면 새로운걸로 간주되어 createdAt 갱신됨
        CareerEntity existingCareerEntity = careerRepository.findById(careerDto.getCareerPid())
                .orElseThrow(() -> new IllegalArgumentException("해당 경력을 찾을 수 없습니다."));

        existingCareerEntity.setCareerPid(careerDto.getCareerPid());

        // DTO의 값으로 기존 엔티티를 갱신
        modelMapper.map(careerDto, existingCareerEntity); // DTO -> 엔티티로 한 번에 매핑

        CareerEntity save = careerRepository.save(existingCareerEntity);

        return new ResObj(save);
    }
}
