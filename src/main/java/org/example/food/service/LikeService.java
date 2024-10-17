package org.example.food.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.like.Like;
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
    public void like(User user, Long videoId) {
        Optional<Video> video = videoRepository.findByIdWithOptimisticLock(videoId);
        if (video.isEmpty()) throw new VideoException(VideoExceptionType.NOT_FOUND_VIDEO);
        Video boardPost = video.get();

        Optional<Like> foundLike = null;
        foundLike = likeRepository.findByUserAndVideo(user, boardPost);

        if (foundLike.isEmpty()) {
            Like like = Like.builder()
                    .user(user)
                    .video(boardPost)
                    .build();
            likeRepository.save(like);
            boardPost.addLikeCount(1);
        } else {
            likeRepository.deleteByUserAndVideo(user, boardPost);
            boardPost.addLikeCount(-1);
        }
    }
}
