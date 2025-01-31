package org.example.food.common.error.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    HttpStatus httpStatus();

    String errorMessage();
}
