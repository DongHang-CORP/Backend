package org.example.food.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    HttpStatus httpStatus();

    String errorMessage();
}
