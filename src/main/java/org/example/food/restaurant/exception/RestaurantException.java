package org.example.food.restaurant.exception;

import lombok.RequiredArgsConstructor;
import org.example.food.common.error.exception.BaseException;
import org.example.food.common.error.exception.BaseExceptionType;

@RequiredArgsConstructor
public class RestaurantException extends BaseException {
    private final RestaurantExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
