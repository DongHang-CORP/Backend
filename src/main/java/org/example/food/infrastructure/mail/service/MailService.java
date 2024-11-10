package org.example.food.infrastructure.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.HashMap;

public interface MailService {
    MimeMessage createMail(String mail, int number) throws MessagingException;

    HashMap<String, Object> sendMailWithCode(String mail);
}
