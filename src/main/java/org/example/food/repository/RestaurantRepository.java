package org.example.food.repository;

import org.example.food.entity.Restaurant;
import org.example.food.entity.Video;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    Restaurant save(Restaurant restaurant);
    void deleteById(Long id);

}
