package org.example.food.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.board.dto.BoardResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantDetailsDto {

    private Long id;
    private String name;
    private double lat;
    private double lng;


    public RestaurantDetailsDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.lat = restaurant.getLocation().getLatitude();
        this.lng = restaurant.getLocation().getLongitude();
    }
}
