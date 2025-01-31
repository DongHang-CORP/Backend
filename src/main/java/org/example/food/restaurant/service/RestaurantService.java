package org.example.food.restaurant.service;

import org.example.food.restaurant.dto.RestaurantDetailsDto;
import org.example.food.restaurant.dto.RestaurantResDto;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.user.entity.User;

import java.util.List;

public interface RestaurantService {
    List<RestaurantResDto> getAllRestaurants();


    void deleteRestaurant(Long id);

    Restaurant findRestaurantById(Long id);

}
