package org.example.food.restaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.global.common.dto.Location;
import org.example.food.restaurant.dto.RestaurantDetailsDto;
import org.example.food.restaurant.dto.RestaurantResDto;
import org.example.food.restaurant.entity.Category;
import org.example.food.restaurant.service.RestaurantServiceImpl;
import org.example.food.user.dto.CustomUserDetails;
import org.example.food.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
@Slf4j
public class RestaurantController {

    private final RestaurantServiceImpl restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResDto>> getAllRestaurants() {
        List<RestaurantResDto> restaurantResDtos = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurantResDtos);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<RestaurantResDto>> getNearbyRestaurants(
            @RequestBody Location location,
            @RequestParam(required = false) List<Category> categories) {

        List<RestaurantResDto> restaurants = restaurantService.getNearbyRestaurants(location, categories);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailsDto> getRestaurantById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = (userDetails != null) ? userDetails.getUser() : null;
        RestaurantDetailsDto restaurant = restaurantService.getRestaurantById(id, user);
        return ResponseEntity.ok(restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

}
