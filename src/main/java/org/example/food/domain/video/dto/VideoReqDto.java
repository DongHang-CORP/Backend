package org.example.food.domain.video.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.domain.video.Category;

@Data
@Builder
public class VideoReqDto {
    private String restaurant;
    private String url;
    private String content;
    private Category category;
    private double lat;
    private double lng;
}
