package org.example.food.restaurant.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.food.board.entity.Address;
import org.example.food.board.entity.Board;
import org.example.food.board.entity.Category;
import org.example.food.board.entity.Location;

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

    @Embedded
    private Location location;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @Builder
    private Restaurant(String name, Location location, Address address, Category category) {
        this.name = name;
        this.location = location;
        this.address = address;
        this.category = category;
    }

    public static Restaurant of(String name, Location location, Address address, Category category) {
        return Restaurant.builder()
                .name(name)
                .location(location)
                .address(address)
                .category(category)
                .build();
    }
}
