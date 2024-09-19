package org.example.food.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.common.exception.BaseException;
import org.example.food.common.exception.BaseExceptionType;

@RequiredArgsConstructor
public class UserException extends BaseException {
    private final RestaurantExceptionType exceptionType;
    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
