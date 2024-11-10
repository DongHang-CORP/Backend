package org.example.food.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.video.dto.VideoResDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantDetailsDto {

    private Long id;
    private String name;
    private double lat;
    private double lng;
    private List<VideoResDto> videos;


    public RestaurantDetailsDto(Restaurant restaurant, List<VideoResDto> videoResDtos) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.lat = restaurant.getLat();
        this.lng = restaurant.getLng();
        this.videos = videoResDtos;
    }
}
