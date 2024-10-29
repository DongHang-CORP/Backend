package org.example.food.common.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.food.domain.user.dto.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void authRequired() {
    }


    @Before("authRequired() && args(.., userDetails)")
    public void checkAuthentication(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Unauthorized access");
        }
    }
}
