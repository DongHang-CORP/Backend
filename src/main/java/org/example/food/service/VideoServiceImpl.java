package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.exception.VideoException;
import org.example.food.exception.VideoExceptionType;
import org.example.food.repository.VideoQueryRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final RestaurantService restaurantService; // Restaurant 관련 로직 분리
    private final VideoQueryRepository videoQueryRepository;

    // Video -> VideoResDto 변환
    private VideoResDto toVideoDto(Video video) {
        if (video == null) {
            return null;
        }
        return VideoResDto.builder()
                .videoId(video.getId())
                .url(video.getUrl())
                .content(video.getContent())
                .category(video.getCategory())
                .restaurant(video.getRestaurant().getName())
                .restaurantId(video.getRestaurant().getId())
                .userNickname(video.getUser().getNickname())
                .build();
    }

    // VideoReqDto -> Video 변환
    private Video toEntity(VideoReqDto dto) {
        if (dto == null) {
            return null;
        }
        Restaurant restaurant = restaurantService.findOrCreateRestaurant(dto);
        return Video.builder()
                .url(dto.getUrl())
                .content(dto.getContent())
                .category(dto.getCategory())
                .restaurant(restaurant)
                .build();
    }

    @Override
    public List<VideoResDto> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(this::toVideoDto)
                .collect(Collectors.toList());
    }

    @Override
    public VideoResDto getVideoById(Long id) {
        Video video = findVideoById(id);
        return toVideoDto(video);
    }

    @Override
    @Transactional
    public Long createVideo(VideoReqDto videoReqDto, User user) {
        Video video = toEntity(videoReqDto);
        video.setUser(user);
        videoRepository.save(video);
        return video.getId();
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    @Override
    public Video findVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new VideoException(VideoExceptionType.NOT_FOUND_VIDEO));
    }

    @Override
    public List<VideoResDto> getNearbyVideos(double userLat, double userLon, double radius) {
        List<Video> video = videoQueryRepository.findVideosByLocation(userLat, userLon, radius);
        return video.stream()
                .map(this::toVideoDto)
                .collect(Collectors.toList());
    }
}
