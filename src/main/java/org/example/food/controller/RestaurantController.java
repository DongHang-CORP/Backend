package org.example.food.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.dto.RestaurantDetailsDto;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.user.dto.CustomUserDetails;
import org.example.food.domain.video.Category;
import org.example.food.service.RestaurantServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantServiceImpl restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResDto>> getAllRestaurants() {
        List<RestaurantResDto> restaurantResDtos = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurantResDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailsDto> getRestaurantById(@PathVariable Long id) {
        RestaurantDetailsDto restaurant = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createRestaurant(
            @RequestBody RestaurantReqDto restaurantReqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long restaurantId = restaurantService.createRestaurant(restaurantReqDto);
        return new ResponseEntity<>(restaurantId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResDto> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantReqDto restaurant,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        RestaurantResDto restaurantResDto = restaurantService.updateRestaurant(id, restaurant);
        return new ResponseEntity<>(restaurantResDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<RestaurantResDto>> getNearbyRestaurants(
            @RequestParam double userLat,
            @RequestParam double userLon,
            @RequestParam(defaultValue = "5") double radius,
            @RequestParam(required = false) List<Category> categories) {

        List<RestaurantResDto> restaurants = restaurantService.getNearbyRestaurants(userLat, userLon, radius,
                categories);
        return ResponseEntity.ok(restaurants);
    }
}
