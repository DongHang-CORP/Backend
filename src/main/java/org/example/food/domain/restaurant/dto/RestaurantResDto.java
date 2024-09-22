package org.example.food.domain.restaurant.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.domain.video.Category;

@Data
@NoArgsConstructor
public class RestaurantResDto {
    private Long restaurantId;
    private String imageUrl;
    private String name;
    private String address;
    private Category category;
    private String rating;
    private String phoneNumber;
    private double latitude;
    private double longitude;
}
