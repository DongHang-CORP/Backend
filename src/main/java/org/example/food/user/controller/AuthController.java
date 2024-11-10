package org.example.food.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.global.config.security.JWTUtil;
import org.example.food.infrastructure.mail.service.MailServiceImpl;
import org.example.food.user.service.UserService;
import org.example.food.user.dto.UserReqDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final MailServiceImpl mailServiceImpl;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(@RequestBody HashMap<String, String> payload) {
        HashMap<String, Object> map = new HashMap<>();
        String mail = payload.get("mail");

        if (mail == null || mail.isEmpty()) {
            map.put("success", Boolean.FALSE);
            map.put("error", "Email address cannot be empty.");
            return map;
        }

        try {
            HashMap<String, Object> response = mailServiceImpl.sendMailWithCode(mail);
            map.put("success", response.get("success"));
            map.put("number", response.get("number"));
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    @GetMapping("/loginCheck")
    public ResponseEntity<?> loginCheck(@RequestParam String email, @RequestParam String userNumber) {
        if (!mailServiceImpl.verifyMailCode(email, userNumber)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long userId = userService.findUserByEmail(email);
        String token;

        if (userId != 0L) {
            token = jwtUtil.createJwt(userId, email, "ADMIN", 60 * 60 * 10L * 7 * 100);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.SEE_OTHER);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserReqDto userReqDto) {
        Long userId = userService.createUser(userReqDto);
        String token = jwtUtil.createJwt(userId, userReqDto.getEmail(), "ADMIN", 60 * 60 * 10L * 7 * 100);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
