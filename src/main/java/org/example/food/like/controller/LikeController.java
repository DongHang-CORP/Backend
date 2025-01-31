package org.example.food.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.like.dto.LikeFlipResponse;
import org.example.food.like.service.LikeService;
import org.example.food.user.entity.User;
import org.example.food.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class LikeController {

    private final LikeService likeService;
    private final UserRepository userRepository;
    @PutMapping("/{boardId}/like")
    public ResponseEntity<LikeFlipResponse> likeBoard(
            @PathVariable Long boardId) {
        User user = userRepository.findById(1L).orElseThrow();
//        LoginUser User user
        LikeFlipResponse likeFlipResponse = likeService.flipboardLike(boardId, user);
        return new ResponseEntity<>(likeFlipResponse, HttpStatus.OK);
    }
}