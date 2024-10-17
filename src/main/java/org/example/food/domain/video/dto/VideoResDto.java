package org.example.food.domain.video.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.domain.video.Category;

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
}
