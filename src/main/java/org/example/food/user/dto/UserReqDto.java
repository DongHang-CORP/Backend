package org.example.food.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReqDto {
    private String email;
    private String username;
    private String nickname;
}
