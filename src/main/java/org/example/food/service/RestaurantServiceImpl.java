package org.example.food.service;

import org.example.food.entity.Restaurant;
import org.example.food.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    private static final double SEARCH_RADIUS = 5.0; // 5km 반경


    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long id, Restaurant restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(id);
        if (existingRestaurant != null) {
            existingRestaurant.setName(restaurant.getName());
            existingRestaurant.setAddress(restaurant.getAddress());
            existingRestaurant.setCategory(restaurant.getCategory());
            existingRestaurant.setRating(restaurant.getRating());
            existingRestaurant.setPhoneNumber(restaurant.getPhoneNumber());
            return restaurantRepository.save(existingRestaurant);
        }
        return null;
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<Restaurant> getNearbyRestaurants(double userLat, double userLon) {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();

        // 사용자의 위치에서 반경 5km 내에 있는 레스토랑 필터링
        return allRestaurants.stream()
                .filter(restaurant -> {
                    double distance = LocationService.calculateDistance(
                            userLat, userLon, restaurant.getLatitude(), restaurant.getLongitude()
                    );
                    return distance <= SEARCH_RADIUS;  // 5km 반경 내
                })
                .collect(Collectors.toList());
    }
}
