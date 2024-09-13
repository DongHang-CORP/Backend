package org.example.food.entity;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    HttpStatus httpStatus();

    String errorMessage();
}
