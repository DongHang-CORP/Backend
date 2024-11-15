package org.example.food.user.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.global.error.exception.BaseException;
import org.example.food.global.error.exception.BaseExceptionType;

@RequiredArgsConstructor
public class OAuth2Exception extends BaseException {
    private final OAuth2ExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
