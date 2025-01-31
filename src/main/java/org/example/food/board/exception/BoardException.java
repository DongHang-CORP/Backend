package org.example.food.board.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.common.error.exception.BaseException;
import org.example.food.common.error.exception.BaseExceptionType;

@RequiredArgsConstructor
public class BoardException extends BaseException {
    private final BoardExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
