package org.example.food.user.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.global.error.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor

public enum OAuth2ExceptionType implements BaseExceptionType {
    OAUTH2_PROVIDER_ERROR(BAD_REQUEST, "잘못된 OAuth 제공자입니다"),
    OAUTH2_FAILURE(NOT_FOUND, "유저를 찾을 수 없습니다");
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
