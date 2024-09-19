package org.example.food.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 자동 증가
    private Long id;

    private Long userId;

    @ManyToOne // 여러 비디오가 하나의 레스토랑에 속할 수 있으므로 ManyToOne 관계 설정
    @JoinColumn(name = "restaurant_id") // 외래키 설정
    private Restaurant restaurant;

    private String title;
    private String content;
    private int viewCount;
    private String videoUrl;

    public Video() {} // JPA 기본 생성자

    public Video(Long id, Long userId, Restaurant restaurant, String title, String content, int viewCount, String videoUrl) {
        this.id = id;
        this.userId = userId;
        this.restaurant = restaurant;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.videoUrl = videoUrl;
    }
}
