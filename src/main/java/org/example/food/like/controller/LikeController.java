package org.example.food.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.like.dto.LikeReqDto;
import org.example.food.like.service.LikeService;
import org.example.food.user.dto.CustomUserDetails;
import org.example.food.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class LikeController {

    private final LikeService likeService;

    @PutMapping("/video/{videoId}")
    public ResponseEntity<LikeReqDto> likeVideo(
            @PathVariable Long videoId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();
        LikeReqDto like = likeService.like(user, videoId);
        return new ResponseEntity<>(like, HttpStatus.OK);
    }
}
