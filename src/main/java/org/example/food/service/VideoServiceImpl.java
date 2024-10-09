package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.repository.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepository;

    private VideoResDto toVideoDto(Video video) {
        if (video == null) {
            return null;
        }
        VideoResDto dto = new VideoResDto();
        dto.setVideoId(video.getId());
        dto.setUrl(video.getUrl());
        dto.setContent(video.getContent());
        dto.setCategory(video.getCategory());
        dto.setRestaurant(video.getRestaurant().getName());
        return dto;
    }

    private Video toEntity(VideoReqDto dto) {
        if (dto == null) {
            return null;
        }
        Video video = new Video();
        video.setUrl(dto.getUrl());
        video.setContent(dto.getContent());
        video.setCategory(dto.getCategory());

        Restaurant restaurant = restaurantRepository.findByName(dto.getRestaurant());
        video.setRestaurant(restaurant);

        return video;
    }

    @Override
    public List<VideoResDto> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        return videos.stream()
                .map(this::toVideoDto) // 매핑 로직 호출
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
        return videoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("비디오가 없습니다."));
    }
}
