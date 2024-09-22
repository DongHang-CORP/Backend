package org.example.food.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReqDto {
    private String email;
    private String password;
}
