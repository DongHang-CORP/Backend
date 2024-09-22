package org.example.food.domain.video;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;

@Entity
@Getter
@Setter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String title;
    private String content;
    private int viewCount;
    private String videoUrl;

    public Video() {}

    public Video(Long id, User userId, Restaurant restaurant, String title, String content, int viewCount, String videoUrl) {
        this.id = id;
        this.user = userId;
        this.restaurant = restaurant;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.videoUrl = videoUrl;
    }
}
