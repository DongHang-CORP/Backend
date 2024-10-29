package org.example.food.restaurant.service;

import org.example.food.restaurant.entity.Restaurant;
import org.example.food.restaurant.dto.RestaurantDetailsDto;
import org.example.food.restaurant.dto.RestaurantReqDto;
import org.example.food.restaurant.dto.RestaurantResDto;
import org.example.food.user.entity.User;
import org.example.food.restaurant.entity.Category;
import org.example.food.video.dto.VideoReqDto;

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