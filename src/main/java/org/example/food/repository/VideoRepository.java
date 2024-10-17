package org.example.food.repository;

import jakarta.persistence.LockModeType;
import org.example.food.domain.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByRestaurantId(Long restaurantId);
    long countByRestaurantId(Long restaurantId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select v from Video v where v.id = :videoId")
    Optional<Video> findByIdWithOptimisticLock(@Param("videoId") Long videoId);
}
