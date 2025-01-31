package org.example.food.restaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.restaurant.dto.RestaurantDetailsDto;
import org.example.food.restaurant.dto.RestaurantResDto;
import org.example.food.restaurant.service.RestaurantServiceImpl;
import org.example.food.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
@Slf4j
public class RestaurantController {

    private final RestaurantServiceImpl restaurantService;
//
//    @GetMapping
//    public ResponseEntity<List<RestaurantResDto>> getAllRestaurants() {
//        List<RestaurantResDto> restaurantResDtos = restaurantService.getAllRestaurants();
//        return ResponseEntity.ok(restaurantResDtos);
//    }

//    @GetMapping("/nearby")
//    public ResponseEntity<List<RestaurantResDto>> getNearbyRestaurants(
//            @RequestBody Location location,
//            @RequestParam(required = false) List<Category> categories) {
//
//        List<RestaurantResDto> restaurants = restaurantService.getNearbyRestaurants(location, categories);
//        return ResponseEntity.ok(restaurants);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<RestaurantDetailsDto> getRestaurantById(@PathVariable Long id) {
//        User user = null;
//        RestaurantDetailsDto restaurant = restaurantService.getRestaurantById(id, user);
//        return ResponseEntity.ok(restaurant);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
//        restaurantService.deleteRestaurant(id);
//        return ResponseEntity.noContent().build();
//    }

}
