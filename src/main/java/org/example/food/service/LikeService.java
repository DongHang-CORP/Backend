package org.example.food.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.like.Like;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final VideoRepository videoRepository;

    @Transactional
    public String like(User user, Long postid) {
        Optional<Video> video = videoRepository.findById(postid);
        if (video.isEmpty()) throw new IllegalArgumentException("해당 공지사항이 존재하지 않습니다.");
        Video boardPost = video.get();
        log.info("좋아요 기능");

        Optional<Like> foundLike = null;
        foundLike = likeRepository.findByUserAndBoardPost(user, boardPost);

        if (foundLike.isEmpty()) {
            Like like = Like.builder()
                    .user(user)
                    .video(boardPost)
                    .build();
            likeRepository.save(like);
            boardPost.addLikeCount(1);
            return "좋아요!";
        } else {
            likeRepository.deleteByUserAndBoardPost(user, boardPost);
            boardPost.addLikeCount(-1);
            return "좋아요 취소!";
        }
    }
}
