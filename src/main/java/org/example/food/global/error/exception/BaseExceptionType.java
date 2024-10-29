package org.example.food.global.error.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    HttpStatus httpStatus();

    String errorMessage();
}
