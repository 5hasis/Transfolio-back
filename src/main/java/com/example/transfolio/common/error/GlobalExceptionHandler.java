package com.example.transfolio.common.error;

import com.example.transfolio.common.response.ResObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResObj> handleBusinessException(BusinessException ex) {
        // BusinessException에서 ResObj로 변환하여 반환
        ResObj resObj = ex.toResObj();
        return new ResponseEntity<>(resObj, ex.getHttpStatus()); // BusinessException에 정의된 상태 코드로 응답
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResObj> handleGenericException(Exception ex) {
        // 일반 예외 처리: 500 내부 서버 오류
        log.error("서버 오류 발생", ex);
        ResObj resObj = new ResObj("500", "서버 내부 오류");
        return new ResponseEntity<>(resObj, HttpStatus.INTERNAL_SERVER_ERROR); // 500 상태 코드로 응답
    }
}
