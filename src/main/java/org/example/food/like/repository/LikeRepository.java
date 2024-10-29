package org.example.food.like.repository;

import org.example.food.like.entity.Like;
import org.example.food.user.entity.User;
import org.example.food.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByUserAndVideo(User user, Video video);

    void deleteByUserAndVideo(User user, Video video);
}
