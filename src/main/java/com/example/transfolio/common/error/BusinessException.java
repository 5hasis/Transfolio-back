package com.example.transfolio.common.error;

import com.example.transfolio.common.response.ResObj;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final ErrorEnum errorEnum;
    private final HttpStatus httpStatus;

    // 기본 생성자: HttpStatus.BAD_REQUEST (400)
    public BusinessException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    // 명시적으로 상태 코드를 지정할 수 있는 생성자
    public BusinessException(ErrorEnum errorEnum, HttpStatus httpStatus) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
        this.httpStatus = httpStatus;
    }


    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    // ResObj로 변환하는 메서드
    public ResObj toResObj() {
        return new ResObj(this.errorEnum.getStatus(), this.errorEnum.getMessage());
    }
}
