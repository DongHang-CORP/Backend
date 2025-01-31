package org.example.food.restaurant.dto;

import lombok.Builder;
import lombok.Data;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.board.entity.Category;

@Data
@Builder
public class RestaurantResDto {
    private Long restaurantId;
    private String name;
    private double latitude;
    private double longitude;
    private Category category;
    private Long boardCount;

    public static RestaurantResDto toRestaurantDto(Restaurant restaurant) {

        return RestaurantResDto.builder()
                .restaurantId(restaurant.getId())
                .name(restaurant.getName())
                .latitude(restaurant.getLocation().getLatitude())
                .longitude(restaurant.getLocation().getLongitude())
                .category(restaurant.getCategory())
                .build();
    }
}
