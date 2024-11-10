package org.example.food.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResDto {
    private Long userId;
    private String username;
    private String nickname;
    private String email;
}
