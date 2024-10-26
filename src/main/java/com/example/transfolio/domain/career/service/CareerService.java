package com.example.transfolio.domain.career.service;

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
}
