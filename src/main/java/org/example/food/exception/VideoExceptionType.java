package org.example.food.exception;

import org.example.food.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum VideoExceptionType implements BaseExceptionType {
    ;

    @Override
    public int errorCode() {
        return 0;
    }

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String errorMessage() {
        return null;
    }
}
