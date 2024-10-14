package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.repository.RestaurantRepository;
import org.example.food.repository.UserRepository;
import org.example.food.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
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
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByEmail(name);
        User user = new User(1L,
                "johndoe",                    // username
                "박민수ㅂㅅ",                  // nickname
                "profile.jpg",                // profileImage
                "john.doe@example.com",       // email
                "USER"                        // role
        );
        userRepository.save(user);
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 인증되지 않음
//        }

        if(restaurantRepository.findByName(videoReqDto.getRestaurant())!=null){

        }else {
            log.info("video category: {}", videoReqDto.getCategory());
            Restaurant restaurant = new Restaurant();
            restaurant.setName(videoReqDto.getRestaurant());
            restaurant.setLat(videoReqDto.getLat());
            restaurant.setLng(videoReqDto.getLng());

            restaurant.setCategory(videoReqDto.getCategory());
            restaurantRepository.save(restaurant);
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

}
