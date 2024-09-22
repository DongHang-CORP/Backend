package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.domain.video.mapper.VideoMapper;
import org.example.food.repository.RestaurantRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final RestaurantRepository restaurantRepository;
    private final VideoMapper videoMapper;
    private static final double SEARCH_RADIUS = 5.0; // 5km 반경

    @Override
    public List<VideoResDto> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        return videos.stream()
                .map(videoMapper::toVideoDto)
                .collect(Collectors.toList());
    }

    @Override
    public VideoResDto getVideoById(Long id) {
        Video video = findVideoById(id);
        return videoMapper.toVideoDto(video);
    }

    @Override
    public Long createVideo(VideoReqDto videoReqDto) {
        Video video = videoMapper.toEntity(videoReqDto);
        videoRepository.save(video);
        return video.getId();
    }

    @Override
    @Transactional
    public VideoResDto updateVideo(Long id, VideoReqDto videoReqDto) {
        Video video = findVideoById(id);
        videoMapper.updateVideoFromDto(videoReqDto, video);
        videoRepository.save(video);
        return videoMapper.toVideoDto(video);
    }

    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }



    @Override
    public List<VideoResDto> getNearbyVideos(double userLat, double userLon) {
        // 모든 비디오 가져오기
        List<Video> allVideos = videoRepository.findAll();

        // 비디오가 속한 레스토랑의 위치를 기준으로 필터링
        return allVideos.stream()
                .filter(video -> {
                    Restaurant restaurant = restaurantRepository.findById(video.getId()).orElseThrow();
                    double distance = LocationService.calculateDistance(
                            userLat, userLon, restaurant.getLatitude(), restaurant.getLongitude()
                    );
                    return distance <= SEARCH_RADIUS;  // 5km 반경 내
                })
                .map(videoMapper::toVideoDto)
                .collect(Collectors.toList());
    }

    @Override
    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("비디오가 없습니다."));
    }
}

