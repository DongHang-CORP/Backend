package org.example.food.user.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.global.error.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor

public enum UserExceptionType implements BaseExceptionType {
    NOT_FOUND_USER(NOT_FOUND, "유저를 찾을 수 없습니다");;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
