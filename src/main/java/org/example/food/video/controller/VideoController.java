package org.example.food.video.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.global.common.dto.Location;
import org.example.food.global.common.dto.PagingDto;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.user.dto.CustomUserDetails;
import org.example.food.user.entity.User;
import org.example.food.video.dto.VideoReqDto;
import org.example.food.video.dto.VideoResDto;
import org.example.food.video.entity.Video;
import org.example.food.video.service.VideoServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoServiceImpl videoService;

    @GetMapping
    public ResponseEntity<?> getAllVideos(Pageable pageable, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails != null ? userDetails.getUser() : null;
        Page<VideoResDto> videoResDtos = videoService.getAllVideos(pageable, user);

        return ResponseEntity.ok(new PagingDto<>(videoResDtos.getContent(), videoResDtos.getNumber(), videoResDtos.getTotalPages()));
    }

    @PostMapping
    public ResponseEntity<Long> createVideo(
            @RequestBody VideoReqDto videoReqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();

        Restaurant restaurant = videoService.findOrCreateRestaurant(videoReqDto);

        Long videoId = videoService.createVideo(videoReqDto, user, restaurant);
        return ResponseEntity.ok(videoId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Video video = videoService.findVideoById(id);

        if (!video.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyVideos(
            @RequestBody Location location,
            Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails != null ? userDetails.getUser() : null;

        Page<VideoResDto> videoResDtos = videoService.getNearbyVideos(location, pageable, user);

        return ResponseEntity.ok(new PagingDto<>(videoResDtos.getContent(), videoResDtos.getNumber(), videoResDtos.getTotalPages()));
    }
}
