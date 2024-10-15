package org.example.food.service;

import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    List<VideoResDto> getAllVideos();
    VideoResDto getVideoById(Long id);
    Long createVideo(VideoReqDto videoReqDto, User user);
    void deleteVideo(Long id);
    Video findVideoById(Long id);
    List<Video> getNearbyVideos(double userLat, double userLon, double radius);

}
