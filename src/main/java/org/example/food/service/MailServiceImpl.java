package org.example.food.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, Integer> redisTemplate;
    private static final String SENDER_EMAIL = "your-email@gmail.com";

    // 랜덤으로 인증번호 생성
    public int createNumber() {
        return (int) (Math.random() * (900000)) + 100000; // 100000 ~ 999999
    }

    // 이메일 인증 코드 생성 및 Redis 저장
    public HashMap<String, Object> sendMailWithCode(String email) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            int number = createNumber();
            MimeMessage message = createMail(email, number);
            sendMailAsync(message);

            // Redis에 이메일과 인증번호 저장, 5분 유효
            redisTemplate.opsForValue().set(email, number, 5, TimeUnit.MINUTES);

            response.put("success", true);
            response.put("number", number);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    // 이메일 인증 메일 작성
    private MimeMessage createMail(String email, int number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(SENDER_EMAIL);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("이메일 인증");
        String body = "<h3>요청하신 인증 번호입니다.</h3><h1>" + number + "</h1><h3>감사합니다.</h3>";
        message.setText(body, "UTF-8", "html");
        return message;
    }

    // 비동기 메일 전송
    @Async
    public void sendMailAsync(MimeMessage message) {
        javaMailSender.send(message);
    }

    // 이메일 인증번호 확인
    public boolean verifyMailCode(String email, String userNumber) {
        Integer savedNumber = redisTemplate.opsForValue().get(email);
        return savedNumber != null && savedNumber.equals(Integer.parseInt(userNumber));
    }
}
