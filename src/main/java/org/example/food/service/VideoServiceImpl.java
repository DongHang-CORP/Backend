package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.exception.VideoException;
import org.example.food.exception.VideoExceptionType;
import org.example.food.repository.LikeRepository;
import org.example.food.repository.VideoQueryRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final RestaurantService restaurantService;
    private final VideoQueryRepository videoQueryRepository;
    private final LikeRepository likeRepository;

    @Override
    public Slice<VideoResDto> getAllVideos(Pageable pageable, User user) {
        return getVideos(videoQueryRepository.findAllVideosWithPagination(pageable), user, pageable);
    }

    @Override
    public Slice<VideoResDto> getNearbyVideos(double userLat, double userLon, double radius, Pageable pageable, User user) {
        return getVideos(videoQueryRepository.findVideosByLocationWithPagination(userLat, userLon, radius, pageable), user, pageable);
    }

    private Slice<VideoResDto> getVideos(List<Video> videos, User user, Pageable pageable) {
        List<VideoResDto> content = videos.stream()
                .map(video -> toVideoDto(video, user))
                .collect(Collectors.toList());

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(content, pageable, hasNext);
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
    public Long createVideo(VideoReqDto videoReqDto, User user) {
        Restaurant restaurant = restaurantService.findOrCreateRestaurant(videoReqDto);
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
}
