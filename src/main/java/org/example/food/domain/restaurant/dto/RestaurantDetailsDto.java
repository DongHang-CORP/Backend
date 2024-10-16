package org.example.food.domain.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoResDto;

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
