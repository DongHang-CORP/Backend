package org.example.food.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.like.dto.LikeReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid LikeReqDto likeReqDto) throws Exception {
        likeService.insert(likeReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(null); // 201 Created 상태 코드
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody @Valid LikeReqDto likeReqDto) {
        likeService.delete(likeReqDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // 204 No Content 상태 코드
    }

}