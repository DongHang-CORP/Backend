package org.example.food.service;

import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.video.Category;
import org.example.food.domain.video.dto.VideoReqDto;

import java.util.List;

public interface RestaurantService {
    List<RestaurantResDto> getAllRestaurants();
    RestaurantResDto getRestaurantById(Long id);
    Long createRestaurant(RestaurantReqDto restaurantReqDto);
    RestaurantResDto updateRestaurant(Long id, RestaurantReqDto restaurantReqDto);
    void deleteRestaurant(Long id);
    Restaurant findRestaurantById(Long id);
    List<RestaurantResDto> getNearbyRestaurants(double userLat, double userLon, double radius, List<Category> categories);
    Restaurant findOrCreateRestaurant(VideoReqDto videoReqDto);

}
