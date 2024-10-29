package org.example.food.video.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.global.error.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum VideoExceptionType implements BaseExceptionType {

    NOT_FOUND_VIDEO(NOT_FOUND, "비디오를 찾을 수 없습니다");;

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
