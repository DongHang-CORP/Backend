package org.example.food.video.service;

import org.example.food.restaurant.entity.Restaurant;
import org.example.food.user.entity.User;
import org.example.food.video.entity.Video;
import org.example.food.video.dto.VideoReqDto;
import org.example.food.video.dto.VideoResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface VideoService {
    Slice<VideoResDto> getAllVideos(Pageable pageable, User user);

    Long createVideo(VideoReqDto videoReqDto, User user, Restaurant restaurant);

    void deleteVideo(Long id);

    Video findVideoById(Long id);

    Slice<VideoResDto> getNearbyVideos(double userLat, double userLon, double radius, Pageable pageable, User user);
}
