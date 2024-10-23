package org.example.food.controller;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.example.food.common.jwt.JWTUtil;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
import org.example.food.service.MailServiceImpl;
import org.example.food.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final MailServiceImpl mailServiceImpl;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    private int number;

    // 이메일 인증 코드 요청
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

    // 이메일 인증 확인
    @GetMapping("/loginCheck")
    public ResponseEntity<?> loginCheck(@RequestParam String email, @RequestParam String userNumber) {
        boolean isMatch = userNumber.equals(String.valueOf(number));

        if (isMatch) {
            UserResDto userResDto = userService.findUserByEmail(email);
            String token;

            if (userResDto != null) {
                token = jwtUtil.createJwt(email, "role", 60 * 60 * 10L);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + token);
                return new ResponseEntity<>(headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("유저 정보가 없습니다", HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    // 회원가입 처리
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserReqDto userReqDto, @RequestParam String userNumber) {
        // 이메일 인증 확인
        boolean isMatch = userNumber.equals(String.valueOf(number));
        if (!isMatch) {
            return new ResponseEntity<>("Invalid or expired verification code.", HttpStatus.UNAUTHORIZED);
        }

        Long userId = userService.createUser(userReqDto);
        String token = jwtUtil.createJwt(userReqDto.getEmail(), "role", 60 * 60 * 10L);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
