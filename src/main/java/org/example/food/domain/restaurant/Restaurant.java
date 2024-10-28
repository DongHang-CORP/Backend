package org.example.food.domain.restaurant;

import jakarta.persistence.*;
import lombok.*;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.video.Category;
import org.example.food.domain.video.Video;

import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
    private List<Video> videos;

    public static Restaurant of(String name, double lat, double lng, Category category) {
        return Restaurant.builder()
                .name(name)
                .lat(lat)
                .lng(lng)
                .category(category)
                .build();
    }

    public static Restaurant toEntity(RestaurantReqDto dto) {
        return Optional.ofNullable(dto).map(d -> Restaurant.builder()
                .name(d.getName())
                .lat(d.getLatitude())
                .lng(d.getLongitude())
                .category(d.getCategory())
                .build()).orElse(null);
    }

}
