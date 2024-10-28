package org.example.food.domain.video;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
import org.example.food.domain.video.dto.VideoReqDto;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String url;
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int likeCount;
    
    @Version
    private Integer version;

    @Builder
    private Video(User user, Restaurant restaurant, String url, String content, Category category) {
        this.user = user;
        this.restaurant = restaurant;
        this.url = url;
        this.content = content;
        this.category = category;
    }


    public static Video toEntity(VideoReqDto dto, User user, Restaurant restaurant) {
        Video video = new Video();
        video.user = user;
        video.restaurant = restaurant;
        video.url = dto.getUrl();
        video.content = dto.getContent();
        video.category = dto.getCategory();
        video.likeCount = 0;
        return video;
    }

    public int incrementLikeCount() {
        return ++this.likeCount;
    }

    public int decrementLikeCount() {
        return --this.likeCount;
    }
}