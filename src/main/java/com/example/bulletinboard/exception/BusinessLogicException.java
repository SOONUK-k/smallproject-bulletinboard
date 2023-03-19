package com.example.bulletinboard.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessLogicException extends RuntimeException {

    @Getter
    private ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }



}
