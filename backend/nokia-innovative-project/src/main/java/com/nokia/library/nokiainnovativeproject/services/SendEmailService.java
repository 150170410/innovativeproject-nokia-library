package com.nokia.library.nokiainnovativeproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender javaMailSender;

    @Retryable(
            value = MailException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 1000 * 60 * 10))
    public void send(SimpleMailMessage message) {
        javaMailSender.send(message);
    }
}
