package org.example.food.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.food.video.entity.Video;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;

    private double lat;
    private double lng;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    @Builder
    private Restaurant(String name, double lat, double lng, Category category) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.category = category;
    }

    public static Restaurant of(String name, double lat, double lng, Category category) {
        return Restaurant.builder()
                .name(name)
                .lat(lat)
                .lng(lng)
                .category(category)
                .build();
    }
}
