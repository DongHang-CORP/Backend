package org.example.food.video.repository;

import jakarta.persistence.LockModeType;
import org.example.food.restaurant.entity.Category;
import org.example.food.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByCategoryIn(List<Category> categories);
    
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select v from Video v where v.id = :videoId")
    Optional<Video> findByIdWithOptimisticLock(@Param("videoId") Long videoId);
}
