package org.example.food.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Video{
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String title;
    private String content;
    private int viewCount;
    private String videoUrl;
    public Video(Long id, Long userId, Long restaurantId, String title, String content, int viewCount, String videoUrl) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.videoUrl = videoUrl;
    }
}
