package org.example.food.domain.restaurant.dto;

import lombok.Builder;
import lombok.Data;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.video.Category;

@Data
@Builder
public class RestaurantResDto {
    private Long restaurantId;
    private String name;
    private double latitude;
    private double longitude;
    private Category category;
    private Long videoCount;

    public static RestaurantResDto toRestaurantDto(Restaurant restaurant) {
        long videoCount = restaurant.getVideos() != null ? restaurant.getVideos().size() : 0;

        return RestaurantResDto.builder()
                .restaurantId(restaurant.getId())
                .name(restaurant.getName())
                .latitude(restaurant.getLat())
                .longitude(restaurant.getLng())
                .category(restaurant.getCategory())
                .videoCount(videoCount)
                .build();
    }
}
