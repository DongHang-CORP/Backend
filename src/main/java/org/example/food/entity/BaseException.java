package org.example.food.entity;

public abstract class BaseException extends RuntimeException {

    public BaseException() {
    }

    public abstract BaseExceptionType exceptionType();
}
