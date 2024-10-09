package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.repository.UserRepository;
import org.example.food.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    private final UserRepository userRepository;
    @GetMapping
    public ResponseEntity<List<VideoResDto>> getAllVideos() {
        List<VideoResDto> videoResDtos = videoService.getAllVideos();
        return new ResponseEntity<>(videoResDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResDto> getVideoById(@PathVariable Long id) {
        VideoResDto videoResDto = videoService.getVideoById(id);
        return new ResponseEntity<>(videoResDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createVideo(@RequestPart VideoReqDto videoReqDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(name);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 인증되지 않음
        }
        Long videoId = videoService.createVideo(videoReqDto, user);

        return new ResponseEntity<>(videoId, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void deleteVideo(@PathVariable Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(name);
        videoService.deleteVideo(id);
    }
    @GetMapping("/nearby")
    public ResponseEntity<List<VideoResDto>> getNearbyVideos(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        List<VideoResDto> videoResDtos = videoService.getNearbyVideos(latitude, longitude);
        return new ResponseEntity<>(videoResDtos, HttpStatus.OK);
    }
}
