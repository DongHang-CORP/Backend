package org.example.food.service;

import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.video.Video;
import org.example.food.repository.RestaurantRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    private static final double SEARCH_RADIUS = 5.0; // 5km 반경

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public Video getVideoById(Long id) {
        return videoRepository.findById(id).orElseThrow();
    }

    @Override
    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Video updateVideo(Long id, Video video) {
        Video existingVideo = videoRepository.findById(id).orElseThrow();
        if (existingVideo != null) {
            existingVideo.setTitle(video.getTitle());
            existingVideo.setContent(video.getContent());
            existingVideo.setViewCount(video.getViewCount());
            existingVideo.setVideoUrl(video.getVideoUrl());
            return videoRepository.save(existingVideo);
        }
        return null;
    }

    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    @Override
    public List<Video> getNearbyVideos(double userLat, double userLon) {
        // 모든 비디오 가져오기
        List<Video> allVideos = videoRepository.findAll();

        // 비디오가 속한 레스토랑의 위치를 기준으로 필터링
        return allVideos.stream()
                .filter(video -> {
                    Restaurant restaurant = restaurantRepository.findById(video.getId()).orElseThrow();
                    if (restaurant == null) return false;

                    double distance = LocationService.calculateDistance(
                            userLat, userLon, restaurant.getLatitude(), restaurant.getLongitude()
                    );
                    return distance <= SEARCH_RADIUS;  // 5km 반경 내
                })
                .collect(Collectors.toList());
    }
}

