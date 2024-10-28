package org.example.food.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.like.Like;
import org.example.food.domain.like.LikeReqDto;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.exception.VideoException;
import org.example.food.exception.VideoExceptionType;
import org.example.food.repository.LikeRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final VideoRepository videoRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public LikeReqDto like(User user, Long videoId) {
        LikeReqDto likeReqDto = new LikeReqDto();

        Optional<Video> videoOptional = videoRepository.findByIdWithOptimisticLock(videoId);
        if (videoOptional.isEmpty()) {
            throw new VideoException(VideoExceptionType.NOT_FOUND_VIDEO);
        }

        Video boardPost = videoOptional.get();

        Optional<Like> foundLike = likeRepository.findByUserAndVideo(user, boardPost);

        if (foundLike.isEmpty()) {
            Like like = Like.builder()
                    .user(user)
                    .video(boardPost)
                    .build();
            likeRepository.save(like);
            likeReqDto.setLikeCount(boardPost.incrementLikeCount());
            likeReqDto.setLiked(true);
        } else {
            likeRepository.deleteByUserAndVideo(user, boardPost);
            likeReqDto.setLikeCount(boardPost.decrementLikeCount());
            likeReqDto.setLiked(false);
        }

        return likeReqDto;
    }
}
