package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
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
    public ResponseEntity<Long> createVideo(@RequestBody VideoReqDto videoReqDto) {
        Long videoId = videoService.createVideo(videoReqDto);
        return new ResponseEntity<>(videoId, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResDto> updateVideo(@PathVariable Long id, @RequestBody VideoReqDto videoReqDto) {
        VideoResDto videoResDto = videoService.updateVideo(id, videoReqDto);
        return new ResponseEntity<>(videoResDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteVideo(@PathVariable Long id) {
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
