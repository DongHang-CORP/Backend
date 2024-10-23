package org.example.food.common.dto;

import lombok.Data;

@Data
public class PagingResDto<T> {
    private boolean success;
    private String message;
    private T data;
    private boolean nextPage;

    public PagingResDto(boolean success, String message, T data, boolean nextPage) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.nextPage = nextPage;
    }
}
