package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.EmailDTO;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Environment environment;

    public MessageInfo sendSimpleMessage(EmailDTO emailDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDTO.getRecipient().toArray(new String[emailDTO.getRecipient().size()]));
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getMessageContext());

            javaMailSender().send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
            return new MessageInfo(false, exception.toString(), Arrays.asList("Failed to send messages"));
        }
        return MessageInfo.success(null, Arrays.asList("Email sent successfully"));
    }

    private JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", environment.getProperty("mail.debug"));
        return mailSender;
    }
}