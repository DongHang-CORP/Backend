package org.example.food.repository;

import org.example.food.domain.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByRestaurantId(Long restaurantId);
    long countByRestaurantId(Long restaurantId);
}
