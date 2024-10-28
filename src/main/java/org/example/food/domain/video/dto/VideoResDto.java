package org.example.food.domain.video.dto;

import lombok.Builder;
import lombok.Data;
import org.example.food.domain.video.Category;
import org.example.food.domain.video.Video;

@Data
@Builder
public class VideoResDto {
    private Long videoId;
    private String userNickname;
    private Long restaurantId;
    private String restaurant;
    private String url;
    private String content;
    private Category category;
    private int likeCount;
    private boolean isLike;

    public static VideoResDto fromVideo(Video video, boolean isLike) {
        return VideoResDto.builder()
                .videoId(video.getId())
                .userNickname(video.getUser().getNickname())
                .restaurantId(video.getRestaurant().getId())
                .restaurant(video.getRestaurant().getName())
                .url(video.getUrl())
                .content(video.getContent())
                .category(video.getCategory())
                .likeCount(video.getLikeCount())
                .isLike(isLike)
                .build();
    }

}
