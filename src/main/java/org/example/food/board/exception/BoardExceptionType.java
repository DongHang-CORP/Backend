package org.example.food.board.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.common.error.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum BoardExceptionType implements BaseExceptionType {

    NOT_FOUND_board(NOT_FOUND, "비디오를 찾을 수 없습니다");;

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
