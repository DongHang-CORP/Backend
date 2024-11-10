package org.example.food.video.dto;

import lombok.Builder;
import lombok.Data;
import org.example.food.restaurant.entity.Category;

@Data
@Builder
public class VideoReqDto {
    private String restaurant;
    private String url;
    private String content;
    private Category category;
    private double lat;
    private double lng;
}
