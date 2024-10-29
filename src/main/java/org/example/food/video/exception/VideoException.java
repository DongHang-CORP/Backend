package org.example.food.video.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.global.error.exception.BaseException;
import org.example.food.global.error.exception.BaseExceptionType;

@RequiredArgsConstructor
public class VideoException extends BaseException {
    private final VideoExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
