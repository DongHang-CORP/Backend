package org.example.food.repository;

import org.example.food.entity.Restaurant;
import org.example.food.entity.Video;

import java.util.List;
import java.util.Optional;

public interface VideoRepository {
    List<Video> findAll();
    Optional<Video> findById(Long id);
    Video save(Video video);
    void deleteById(Long id);
}
