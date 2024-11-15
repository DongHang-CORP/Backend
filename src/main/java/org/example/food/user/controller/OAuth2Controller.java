package org.example.food.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.food.user.dto.OAuth2LoginDto;
import org.example.food.user.service.OAuth2Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User OAuth2", description = "User OAuth2 API")
@RestController
@RequestMapping("/api/oauth2")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    public OAuth2Controller(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    @Operation(summary = "OAuth2 로그인 화면으로 리다이렉트")
    @GetMapping("/authorization/{registrationId}")
    public ResponseEntity<HttpHeaders> oAuth2Redirect(
            @PathVariable("registrationId") String registrationId
    ) {
        return new ResponseEntity<>(oAuth2Service.oAuth2Redirect(registrationId), HttpStatus.MOVED_PERMANENTLY);
    }

//    @Operation(summary = "OAuth2 로그인 후 Token 발급")
//    @GetMapping("/code/{registrationId}")
//    public ResponseEntity<OAuth2LoginDto> oAuth2Code(
//            @RequestParam String code,
//            @PathVariable("providerId") String registrationId
//    ) {
//        return ResponseEntity.ok(oAuth2Service.oAuth2Login(code, registrationId));
//    }
}