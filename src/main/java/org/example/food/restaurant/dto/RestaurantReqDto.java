package org.example.food.restaurant.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.restaurant.entity.Category;

@Data
@NoArgsConstructor
public class RestaurantReqDto {
    private String name;
    private double latitude;
    private double longitude;
    private Category category;
}
