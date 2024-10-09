package org.example.food.domain.video.mapper;

import org.example.food.common.config.CentralMapperConfig;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(config = CentralMapperConfig.class, componentModel = "spring")
public interface VideoMapper {

    @Mapping(target = "restaurant", expression = "java(mapRestaurantToString(video.getRestaurant()))")
    VideoResDto toVideoDto(Video video);

    Video toEntity(VideoReqDto videoReqDto);

    void updateVideoFromDto(VideoReqDto videoReqDto, @MappingTarget Video video);

    // Restaurant 객체를 String으로 변환하는 메서드
    default String mapRestaurantToString(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        return restaurant.getName();  // Restaurant 객체의 name 필드를 String으로 반환
    }
}