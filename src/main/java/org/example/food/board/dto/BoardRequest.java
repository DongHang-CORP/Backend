package org.example.food.board.dto;

import lombok.Builder;
import lombok.Data;
import org.example.food.board.entity.Address;
import org.example.food.board.entity.Location;
import org.example.food.board.entity.Category;

@Data
@Builder
public class BoardRequest {
    private String restaurantName;
    private String contentUrl;
    private String content;
    private Category category;
    private Location location;
    private Address address;
}
