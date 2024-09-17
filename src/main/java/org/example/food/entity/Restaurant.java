package org.example.food.entity;

public record Restaurant(
        Long id,
        String imageUrl,
        String name,
        String address,
        Category category,
        String rating,
        String phoneNumber
) {
}
