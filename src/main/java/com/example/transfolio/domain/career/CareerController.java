package com.example.transfolio.domain.career;

import com.example.transfolio.common.response.ResObj;
import com.example.transfolio.domain.career.entity.CareerEntity;
import com.example.transfolio.domain.career.model.CareerDto;
import com.example.transfolio.domain.career.service.CareerService;
import com.example.transfolio.security.AuthenticationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 경력 삭제
    @DeleteMapping("/delete/{careerPid}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long careerPid) {
        // 로그인된 사용자 정보
        String loginId = AuthenticationUtil.getLoginIdFromAuthentication();
        careerService.deleteCareer(careerPid, loginId);

        return ResponseEntity.noContent().build(); // 삭제가 성공하면 204 No Content 응답
    }

    @PutMapping("/edit")
    public ResponseEntity<ResObj> editCareer(@RequestBody CareerDto careerDto) throws Exception {
        //token에서 로그인 아이디 가져와서 세팅
        //String loginId = AuthenticationUtil.getLoginIdFromAuthentication();


        ResObj response = careerService.updateCareer(careerDto);

        return ResponseEntity.ok(response);
    }
}
