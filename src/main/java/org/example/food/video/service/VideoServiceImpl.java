package org.example.food.video.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.global.common.dto.Location;
import org.example.food.restaurant.entity.Category;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.user.entity.User;
import org.example.food.video.dto.VideoReqDto;
import org.example.food.video.dto.VideoResDto;
import org.example.food.video.entity.Video;
import org.example.food.video.exception.VideoException;
import org.example.food.video.exception.VideoExceptionType;
import org.example.food.video.repository.VideoQueryRepository;
import org.example.food.video.repository.VideoRepository;
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
    private final VideoDtoMapper videoDtoMapper;

    @Override
    public Page<VideoResDto> getAllVideos(Pageable pageable, User user) {
        Page<Video> videoPage = videoRepository.findAll(pageable);
        return convertToDto(videoPage, user);
    }

    @Override
    public Page<VideoResDto> getNearbyVideos(Location request, Pageable pageable, User user) {
        Page<Video> videoPage = videoQueryRepository.findVideosByLocationWithPagination(request, pageable);
        return convertToDto(videoPage, user);
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

    public List<VideoResDto> getVideosByCategories(List<Category> categories) {
        List<Video> videos = videoRepository.findAllByCategoryIn(categories);
        return videos.stream()
                .map(VideoResDto::of)
                .collect(Collectors.toList());
    }

    private Page<VideoResDto> convertToDto(Page<Video> videoPage, User user) {
        List<VideoResDto> content = videoPage.getContent().stream()
                .map(video -> videoDtoMapper.toVideoDto(video, user))
                .collect(Collectors.toList());

        return new PageImpl<>(content, videoPage.getPageable(), videoPage.getTotalElements());
    }
}
