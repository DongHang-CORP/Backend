package org.example.food.domain.restaurant.mapper;

import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant toEntity(RestaurantReqDto restaurantReqDto);

    RestaurantResDto toRestaurantDto(Restaurant restaurant);

    void updateRestaurantFromDto(RestaurantReqDto restaurantReqDto, @MappingTarget Restaurant restaurant);
}
