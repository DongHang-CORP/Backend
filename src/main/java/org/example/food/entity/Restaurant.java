package org.example.food.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // JPA 엔티티임을 나타냅니다.
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 자동 증가
    private Long id;

    private String imageUrl;
    private String name;
    private String address;

    @Enumerated(EnumType.STRING) // Category 열거형 저장 방식
    private Category category;

    private String rating;
    private String phoneNumber;

    private double latitude;  // 위도
    private double longitude; // 경도

    public Restaurant() {} // JPA에서 기본 생성자는 필수입니다.

    public Restaurant(Long id, String imageUrl, String name, String address, Category category, String rating, String phoneNumber, double latitude, double longitude) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.address = address;
        this.category = category;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
