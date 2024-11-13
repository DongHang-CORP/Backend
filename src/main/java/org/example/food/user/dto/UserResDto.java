package org.example.food.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.food.user.entity.User;

@Data
@NoArgsConstructor
@Getter
public class UserResDto {
    private Long userId;
    private String username;
    private String nickname;
    private String profileImage;
    private String email;
    private String role;
    // private String provider;
    // private String providerId

    public UserResDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        // this.provider = user.getProvider();
        // this.providerId = user.getProviderId();
    }
}
