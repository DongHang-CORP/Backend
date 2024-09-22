package org.example.food.domain.video.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoReqDto {
    private Long userId;
    private Long restaurant_id;
    private String title;
    private String content;
    private int viewCount;
}
