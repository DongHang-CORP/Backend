package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResDto>> getAllRestaurants() {
        List<RestaurantResDto> restaurantResDtos = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurantResDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResDto> getRestaurantById(@PathVariable Long id) {
        RestaurantResDto restaurant = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createRestaurant(@RequestBody RestaurantReqDto restaurantReqDto) {
        Long restaurantId = restaurantService.createRestaurant(restaurantReqDto);
        return new ResponseEntity<>(restaurantId, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResDto> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantReqDto restaurant) {
        RestaurantResDto restaurantResDto = restaurantService.updateRestaurant(id, restaurant);
        return new ResponseEntity<>(restaurantResDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
