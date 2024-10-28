package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.restaurant.dto.RestaurantDetailsDto;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.CustomUserDetails;
import org.example.food.domain.video.Category;
import org.example.food.service.RestaurantServiceImpl;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailsDto> getRestaurantById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = (userDetails != null) ? userDetails.getUser() : null;
        RestaurantDetailsDto restaurant = restaurantService.getRestaurantById(id, user);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<Long> createRestaurant(
            @RequestBody RestaurantReqDto restaurantReqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long restaurantId = restaurantService.createRestaurant(restaurantReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<RestaurantResDto>> getNearbyRestaurants(
            @RequestParam double userLat,
            @RequestParam double userLon,
            @RequestParam(defaultValue = "5") double radius,
            @RequestParam(required = false) List<Category> categories) {

        List<RestaurantResDto> restaurants = restaurantService.getNearbyRestaurants(userLat, userLon, radius, categories);
        return ResponseEntity.ok(restaurants);
    }
}
