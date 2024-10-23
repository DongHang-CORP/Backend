package org.example.food.repository;

import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);
}
