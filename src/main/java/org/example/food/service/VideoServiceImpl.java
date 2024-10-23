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
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final RestaurantService restaurantService; // Restaurant 관련 로직 분리
    private final VideoQueryRepository videoQueryRepository;
    private final LikeRepository likeRepository;

    // Video -> VideoResDto 변환
    private VideoResDto toVideoDto(Video video, User user) {
        if (video == null) {
            return null;
        }
        boolean isLike = likeRepository.findByUserAndVideo(user, video).isPresent();  // 좋아요 여부 확인

        return VideoResDto.builder()
                .videoId(video.getId())
                .url(video.getUrl())
                .content(video.getContent())
                .category(video.getCategory())
                .restaurant(video.getRestaurant().getName())
                .restaurantId(video.getRestaurant().getId())
                .userNickname(video.getUser().getNickname())
                .likeCount(video.getLikeCount())
                .isLike(isLike)
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
    public Slice<VideoResDto> getAllVideos(Pageable pageable, User user) {
        List<Video> videos = videoQueryRepository.findAllVideosWithPagination(pageable);

        List<VideoResDto> content = videos.stream()
                .map(video -> toVideoDto(video, user))  // 현재 사용자를 함께 전달
                .collect(Collectors.toList());

        boolean hasNext = content.size() > pageable.getPageSize();

        if (hasNext) {
            content.remove(pageable.getPageSize());  // 페이지 사이즈 초과시 마지막 요소 제거
        }

        return new SliceImpl<>(content, pageable, hasNext);
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
    public Slice<VideoResDto> getNearbyVideos(double userLat, double userLon, double radius, Pageable pageable, User user) {
        List<Video> videos = videoQueryRepository.findVideosByLocationWithPagination(userLat, userLon, radius, pageable);

        List<VideoResDto> content = videos.stream()
                .map(video -> toVideoDto(video, user))
                .collect(Collectors.toList());

        boolean hasNext = content.size() > pageable.getPageSize();

        if (hasNext) {
            content.remove(pageable.getPageSize());  // 페이지 사이즈 초과시 마지막 요소 제거
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
