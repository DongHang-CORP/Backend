package org.example.food.video.dto;

import lombok.Builder;
import lombok.Data;
import org.example.food.restaurant.entity.Category;
import org.example.food.video.entity.Video;

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

    @Builder
    private VideoResDto(Long videoId, String userNickname, Long restaurantId, String restaurant, String url, String content, Category category, int likeCount, boolean isLike) {
        this.videoId = videoId;
        this.userNickname = userNickname;
        this.restaurantId = restaurantId;
        this.restaurant = restaurant;
        this.url = url;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
        this.isLike = isLike;
    }

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

    public static VideoResDto of(Video video) {
        return VideoResDto.builder()
                .videoId(video.getId())
                .userNickname(video.getUser().getNickname())
                .restaurantId(video.getRestaurant().getId())
                .restaurant(video.getRestaurant().getName())
                .url(video.getUrl())
                .content(video.getContent())
                .category(video.getCategory())
                .likeCount(video.getLikeCount())
                .build();
    }
}
