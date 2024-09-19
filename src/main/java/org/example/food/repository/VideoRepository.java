package org.example.food.repository;

import org.example.food.entity.Restaurant;
import org.example.food.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByRestaurantId(Long restaurantId);
    
}
