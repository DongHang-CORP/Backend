package org.example.food.service;

import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.springframework.data.domain.*;

import java.util.List;

public interface VideoService {
    Slice<VideoResDto> getAllVideos(Pageable pageable);
    VideoResDto getVideoById(Long id);
    Long createVideo(VideoReqDto videoReqDto, User user);
    void deleteVideo(Long id);
    Video findVideoById(Long id);
    Slice<VideoResDto> getNearbyVideos(double userLat, double userLon, double radius, Pageable pageable);
}
