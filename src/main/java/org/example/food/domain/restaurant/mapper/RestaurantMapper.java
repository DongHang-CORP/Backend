package org.example.food.domain.restaurant.mapper;

import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.mapstruct.Mapper;

@Mapper
public interface RestaurantMapper {
    Restaurant toEntity(RestaurantReqDto restaurantReqDto);

    RestaurantResDto toRestaurantDto(Restaurant restaurant);

    void updateRestaurantFromDto(RestaurantReqDto restaurantReqDto, Restaurant restaurant);
}
