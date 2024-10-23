package org.example.food.repository;

import org.example.food.domain.like.Like;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByUserAndVideo(User user, Video video);
    void deleteByUserAndVideo(User user, Video video);
}
