package org.example.food.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.user.entity.User;

@Data
@NoArgsConstructor
public class OAuth2LoginDto {

    private User user;

    private String accessToken;

    private String refreshToken;

    public OAuth2LoginDto(User user, String accessToken, String refreshToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}