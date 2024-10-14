package org.example.food.exception;

import org.example.food.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum UserExceptionType implements BaseExceptionType {
    ;

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String errorMessage() {
        return null;
    }
}
