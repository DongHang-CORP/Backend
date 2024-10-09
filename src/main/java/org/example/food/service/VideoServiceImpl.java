package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.domain.video.mapper.VideoMapper;
import org.example.food.repository.RestaurantRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final RestaurantRepository restaurantRepository;
    private final VideoMapper videoMapper;
    private final FileService fileService;
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
    public Long createVideo(VideoReqDto videoReqDto, User user) {
        Video video = videoMapper.toEntity(videoReqDto);
        video.setUser(user);

        videoRepository.save(video);
        return video.getId();
    }

    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }



    @Override
    public List<VideoResDto> getNearbyVideos(double userLat, double userLon) {
        List<Video> allVideos = videoRepository.findAll();

        return allVideos.stream()
                .filter(video -> {
                    Restaurant restaurant = restaurantRepository.findById(video.getId()).orElseThrow();
                    double distance = LocationService.calculateDistance(
                            userLat, userLon, restaurant.getLatitude(), restaurant.getLongitude()
                    );
                    return distance <= SEARCH_RADIUS;
                })
                .map(videoMapper::toVideoDto)
                .collect(Collectors.toList());
    }

    @Override
    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("비디오가 없습니다."));
    }
}

