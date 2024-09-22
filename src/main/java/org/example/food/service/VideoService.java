package org.example.food.service;

import org.example.food.domain.video.Video;
import java.util.List;

public interface VideoService {
    List<Video> getAllVideos();
    Video getVideoById(Long id);
    Video createVideo(Video video);
    Video updateVideo(Long id, Video video);
    void deleteVideo(Long id);
    List<Video> getNearbyVideos(double userLat, double userLon);

}
