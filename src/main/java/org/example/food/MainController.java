package org.example.food;

import org.example.food.user.dto.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public String mainView(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "Main: 인증되지 않은 사용자입니다.";
        }
        String email = userDetails.getEmail();
        return "Main: " + email + userDetails.getUserId() + userDetails.getNickname();
    }
}
