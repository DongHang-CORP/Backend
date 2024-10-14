package org.example.food.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.common.exception.BaseException;
import org.example.food.common.exception.BaseExceptionType;

@RequiredArgsConstructor
public class VideoException extends BaseException {
    private final VideoExceptionType exceptionType;
    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
