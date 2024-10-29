package org.example.food.common.dto;

import lombok.Data;

@Data
public class PagingResDto<T> {
    private T data;
    private int page;
    private int totalPage;
    private boolean nextPage;

    public PagingResDto(T data, int page, int totalPage, boolean nextPage) {
        this.data = data;
        this.page = page + 1;
        this.totalPage = totalPage;
        this.nextPage = nextPage;
    }
}
