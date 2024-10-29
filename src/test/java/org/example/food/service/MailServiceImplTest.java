package org.example.food.service;

import org.example.food.infrastructure.mail.service.MailServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailServiceImpl mailService;
//
//    @Test
//    void testCreateNumber() {
//        MailServiceImpl.createNumber();
//        int number = MailServiceImpl.getNumber();
//
//        // 생성된 번호가 100000 ~ 999999 사이의 값인지 확인
//        assert(number >= 100000 && number <= 999999);
//    }
//
//    @Test
//    void testCreateMail() throws Exception {
//        // given
//        String recipientEmail = "test@example.com";
//        MimeMessage mimeMessage = mock(MimeMessage.class);
//        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//        // when
//        MimeMessage message = mailService.CreateMail(recipientEmail);
//
//        // then
//        verify(mimeMessage, times(1)).setFrom(any(String.class));
//        verify(mimeMessage, times(1)).setRecipients(eq(MimeMessage.RecipientType.TO), eq(recipientEmail));
//        verify(mimeMessage, times(1)).setSubject("이메일 인증");
//        verify(mimeMessage, times(1)).setText(anyString(), eq("UTF-8"), eq("html"));
//    }
//
//    @Test
//    void testSendMail() {
//        // given
//        String recipientEmail = "test@example.com";
//        MimeMessage mimeMessage = mock(MimeMessage.class);
//        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//        // when
//        int result = mailService.sendMail(recipientEmail);
//
//        // then
//        // 메일 전송이 호출되었는지 확인
//        verify(javaMailSender, times(1)).send(mimeMessage);
//        // 랜덤 생성된 번호가 반환되었는지 확인
//        assert(result >= 100000 && result <= 999999);
//    }

}