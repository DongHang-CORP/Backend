package org.example.food.domain.restaurant.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.domain.video.Category;

@Data
@NoArgsConstructor
public class RestaurantReqDto {
    private String name;
    private double latitude;
    private double longitude;
    private Category category;
}
