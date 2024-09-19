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
        return restaurantRepository.findById(id).orElseThrow();
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long id, Restaurant restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(id).orElseThrow();
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
        // 기본적으로 위도/경도 범위를 이용해 먼저 필터링
        double latRange = SEARCH_RADIUS / 110.574; // 위도의 1도당 거리
        double lonRange = SEARCH_RADIUS / (111.320 * Math.cos(Math.toRadians(userLat))); // 경도의 1도당 거리

        // 위도/경도 범위 내의 레스토랑 검색
        List<Restaurant> nearbyRestaurants = restaurantRepository.findByLatitudeBetweenAndLongitudeBetween(
                userLat - latRange, userLat + latRange,
                userLon - lonRange, userLon + lonRange
        );

        // 하버사인 공식으로 정확한 거리 계산 후 필터링
        return nearbyRestaurants.stream()
                .filter(restaurant -> {
                    double distance = LocationService.calculateDistance(
                            userLat, userLon, restaurant.getLatitude(), restaurant.getLongitude()
                    );
                    return distance <= SEARCH_RADIUS;  // 반경 내의 레스토랑만 반환
                })
                .collect(Collectors.toList());
    }

}
