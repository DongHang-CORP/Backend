package org.example.food.entity;

import java.time.LocalDateTime;

public record Video(
        Long id,
        Long celebId,
        Long restaurantId,
        String title,
        int viewCount,
        String videoUrl,
        LocalDateTime publishedDate
) {
}
