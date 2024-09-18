package org.example.food.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurant{
    private Long id;
    private String imageUrl;
    private String name;
    private String address;
    private Category category;
    private String rating;
    private String phoneNumber;
    private double latitude;
    private double longitude;
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
