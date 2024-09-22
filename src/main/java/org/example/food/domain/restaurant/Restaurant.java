package org.example.food.domain.restaurant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.food.domain.video.Category;

@Entity // JPA 엔티티임을 나타냅니다.
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String rating;
    private String phoneNumber;

    private double latitude;
    private double longitude;

    public Restaurant() {}

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
