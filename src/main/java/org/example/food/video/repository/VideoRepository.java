package org.example.food.video.repository;

import jakarta.persistence.LockModeType;
import org.example.food.user.entity.User;
import org.example.food.video.entity.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByRestaurantId(Long restaurantId);

    long countByRestaurantId(Long restaurantId);

    @Query("SELECT v FROM Video v WHERE v.user = :user")
    Slice<Video> findAllVideos(Pageable pageable, User user);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select v from Video v where v.id = :videoId")
    Optional<Video> findByIdWithOptimisticLock(@Param("videoId") Long videoId);
}
