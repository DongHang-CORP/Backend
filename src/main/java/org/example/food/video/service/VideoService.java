package org.example.food.video.service;

import org.example.food.global.common.dto.Location;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.user.entity.User;
import org.example.food.video.dto.VideoReqDto;
import org.example.food.video.dto.VideoResDto;
import org.example.food.video.entity.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface VideoService {
    Slice<VideoResDto> getAllVideos(Pageable pageable, User user);

    Long createVideo(VideoReqDto videoReqDto, User user, Restaurant restaurant);

    void deleteVideo(Long id);

    Video findVideoById(Long id);

    Slice<VideoResDto> getNearbyVideos(Location location, Pageable pageable, User user);
}
