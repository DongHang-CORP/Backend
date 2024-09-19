package org.example.food.service;

import org.example.food.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(Long id);
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Long id, Restaurant restaurant);
    void deleteRestaurant(Long id);
    List<Restaurant> getNearbyRestaurants(double userLat, double userLon);

}
