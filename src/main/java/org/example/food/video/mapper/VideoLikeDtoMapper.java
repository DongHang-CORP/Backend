package org.example.food.video.mapper;

import lombok.RequiredArgsConstructor;
import org.example.food.like.repository.LikeRepository;
import org.example.food.user.entity.User;
import org.example.food.video.dto.VideoResDto;
import org.example.food.video.entity.Video;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoLikeDtoMapper {
    private final LikeRepository likeRepository;

    public VideoResDto toVideoDto(Video video, User user) {
        boolean isLike = isVideoLiked(video, user);
        return VideoResDto.fromVideo(video, isLike);
    }

    private boolean isVideoLiked(Video video, User user) {
        return user != null && likeRepository.findByUserAndVideo(user, video).isPresent();
    }
}
