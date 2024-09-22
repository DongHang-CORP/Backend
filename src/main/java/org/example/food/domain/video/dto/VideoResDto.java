package org.example.food.domain.video.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoResDto {
    private Long videoId;
    private Long userId;
    private Long restaurantId;
    private String title;
    private String content;
    private int viewCount;
    private String videoUrl;
}
