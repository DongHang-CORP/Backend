package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.common.dto.PagingResDto;
import org.example.food.domain.like.LikeReqDto;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.CustomUserDetails;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.repository.RestaurantRepository;
import org.example.food.service.LikeService;
import org.example.food.service.VideoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    private final RestaurantRepository restaurantRepository;
    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<?> getAllVideos(Pageable pageable, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails != null ? userDetails.getUser() : null;
        Slice<VideoResDto> videoResDtos = videoService.getAllVideos(pageable, user);
        boolean hasNextPage = videoResDtos.hasNext();

        return ResponseEntity.ok(new PagingResDto<>(true, "조회 성공", videoResDtos.getContent(), hasNextPage));
    }

    @PostMapping
    public ResponseEntity<Long> createVideo(
            @RequestBody VideoReqDto videoReqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();

        Restaurant restaurant = restaurantRepository.findByName(videoReqDto.getRestaurant())
                .orElseGet(() -> {
                    Restaurant newRestaurant = Restaurant.of(
                            videoReqDto.getRestaurant(),
                            videoReqDto.getLat(),
                            videoReqDto.getLng(),
                            videoReqDto.getCategory()
                    );
                    restaurantRepository.save(newRestaurant);
                    log.info("Created restaurant: {}", newRestaurant.getName());
                    return newRestaurant;
                });

        Long videoId = videoService.createVideo(videoReqDto, user);
        return ResponseEntity.ok(videoId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Video video = videoService.findVideoById(id);

        if (video == null) {
            return ResponseEntity.notFound().build();
        }

        if (!video.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyVideos(
            @RequestParam double userLat,
            @RequestParam double userLon,
            @RequestParam(defaultValue = "5") double radius,
            Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails != null ? userDetails.getUser() : null;
        Slice<VideoResDto> videos = videoService.getNearbyVideos(userLat, userLon, radius, pageable, user);
        boolean hasNextPage = videos.hasNext();

        return ResponseEntity.ok(new PagingResDto<>(true, "조회 성공", videos.getContent(), hasNextPage));
    }

    @PutMapping("/{videoId}/like")
    public ResponseEntity<LikeReqDto> likeVideo(@PathVariable Long videoId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userDetails.getUser();
        LikeReqDto like = likeService.like(user, videoId);
        return new ResponseEntity<>(like, HttpStatus.OK);
    }
}
