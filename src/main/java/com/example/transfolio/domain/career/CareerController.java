package com.example.transfolio.domain.career;

import com.example.transfolio.domain.career.entity.CareerEntity;
import com.example.transfolio.domain.career.service.CareerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/career")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @PostMapping("/regist")
    public ResponseEntity<CareerEntity> createCareer(@RequestBody CareerEntity careerEntity) {
        CareerEntity savedCareer = careerService.saveCareer(careerEntity);
        return new ResponseEntity<>(savedCareer, HttpStatus.CREATED);
    }
}
