package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.common.jwt.JWTUtil;
import org.example.food.service.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailServiceImpl mailServiceImpl;
    private final JWTUtil jwtUtil;

    private int number; // 이메일 인증 숫자를 저장하는 변수

    // 인증 이메일 전송
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
            number = mailServiceImpl.sendMail(mail);
            String num = String.valueOf(number);

            map.put("success", Boolean.TRUE);
            map.put("number", num);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }
    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String email, @RequestParam String userNumber) {
        // 이메일 인증번호가 일치하는지 확인
        boolean isMatch = userNumber.equals(String.valueOf(number));

        if (isMatch) {
            // JWT 생성 시 username으로 email 값을 사용
            String token = jwtUtil.createJwt(email, "role", 60 * 60 * 10L);
            HttpHeaders headers = new HttpHeaders();

            // Authorization 헤더에 Bearer 토큰 추가
            headers.add("Authorization", "Bearer " + token);
            return new ResponseEntity<>(isMatch, headers, HttpStatus.OK);
        }

        // 인증 실패 시 응답
        return new ResponseEntity<>(isMatch, HttpStatus.OK);
    }

}
