package org.example.food.global.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Location {
    private Double userLat = 37.5665;
    private Double userLon = 126.9780;
    private Double radius = 5.0;

    public Location() {
    }

}
