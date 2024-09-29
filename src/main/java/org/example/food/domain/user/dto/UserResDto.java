package org.example.food.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResDto {
    private String userId;
    private String username;
    private String nickname;
    private String email;
}
