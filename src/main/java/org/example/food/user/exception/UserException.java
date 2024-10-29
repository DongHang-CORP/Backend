package org.example.food.user.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.global.error.exception.BaseException;
import org.example.food.global.error.exception.BaseExceptionType;

@RequiredArgsConstructor
public class UserException extends BaseException {
    private final UserExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
