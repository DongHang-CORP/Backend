package org.example.food.service;

import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantDetailsDto;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Category;
import org.example.food.domain.video.dto.VideoReqDto;

import java.util.List;

public interface RestaurantService {
    List<RestaurantResDto> getAllRestaurants();

    RestaurantDetailsDto getRestaurantById(Long id, User user);

    Long createRestaurant(RestaurantReqDto restaurantReqDto);

    void deleteRestaurant(Long id);

    Restaurant findRestaurantById(Long id);

    List<RestaurantResDto> getNearbyRestaurants(double userLat, double userLon, double radius, List<Category> categories);

    Restaurant findOrCreateRestaurant(VideoReqDto videoReqDto);

}
