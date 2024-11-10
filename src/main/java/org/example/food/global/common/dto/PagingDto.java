package org.example.food.global.common.dto;

import lombok.Data;

@Data
public class PagingDto<T> {
    private T data;
    private int page;
    private int totalPage;

    public PagingDto(T data, int page, int totalPage) {
        this.data = data;
        this.page = page;
        this.totalPage = totalPage;
    }
}
