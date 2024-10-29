package org.example.food.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.exception.VideoException;
import org.example.food.exception.VideoExceptionType;
import org.example.food.repository.LikeRepository;
import org.example.food.repository.RestaurantRepository;
import org.example.food.repository.VideoQueryRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoQueryRepository videoQueryRepository;
    private final RestaurantRepository restaurantRepository;
    private final LikeRepository likeRepository;

    @Override
    public Page<VideoResDto> getAllVideos(Pageable pageable, User user) {
        return getVideos(videoQueryRepository.findAllVideosWithPagination(pageable), user, pageable);
    }

    @Override
    public Page<VideoResDto> getNearbyVideos(double userLat, double userLon, double radius, Pageable pageable, User user) {
        return getVideos(videoQueryRepository.findVideosByLocationWithPagination(userLat, userLon, radius, pageable), user, pageable);
    }

    private Page<VideoResDto> getVideos(List<Video> videos, User user, Pageable pageable) {
        List<VideoResDto> content = videos.stream()
                .map(video -> toVideoDto(video, user))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), content.size());

        List<VideoResDto> pageContent = content.subList(start, end);

        return new PageImpl<>(pageContent, pageable, content.size());
    }

    private VideoResDto toVideoDto(Video video, User user) {
        if (video == null) {
            return null;
        }

        boolean isLike = user != null && likeRepository.findByUserAndVideo(user, video).isPresent(); // 좋아요 여부 확인
        return VideoResDto.fromVideo(video, isLike);
    }

    @Override
    @Transactional
    public Long createVideo(VideoReqDto videoReqDto, User user, Restaurant restaurant) {
        Video video = Video.toEntity(videoReqDto, user, restaurant);
        videoRepository.save(video);
        return video.getId();
    }


    @Override
    @Transactional
    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new VideoException(VideoExceptionType.NOT_FOUND_VIDEO);
        }
        videoRepository.deleteById(id);
    }

    @Override
    public Video findVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new VideoException(VideoExceptionType.NOT_FOUND_VIDEO));
    }

    @Transactional
    public Restaurant findOrCreateRestaurant(VideoReqDto videoReqDto) {
        return restaurantRepository.findByName(videoReqDto.getRestaurant())
                .orElseGet(() -> {
                    Restaurant restaurant = Restaurant.of(
                            videoReqDto.getRestaurant(),
                            videoReqDto.getLat(),
                            videoReqDto.getLng(),
                            videoReqDto.getCategory()
                    );
                    restaurantRepository.save(restaurant);
                    return restaurant;
                });
    }

}
