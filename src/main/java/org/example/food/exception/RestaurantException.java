package org.example.food.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.common.exception.BaseException;
import org.example.food.common.exception.BaseExceptionType;

@RequiredArgsConstructor
public class RestaurantException extends BaseException {
    private final RestaurantExceptionType exceptionType;
    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
