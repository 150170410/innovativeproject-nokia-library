package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import com.nokia.library.nokiainnovativeproject.utils.EmailRecipients;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender emailSender;

	@Retryable(
			value = MailException.class,
			maxAttempts = 5,
			backoff = @Backoff(delay = 1000 * 60 * 10))
	public void sendSimpleMessage(Email email) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(EmailRecipients.recipients.toArray(new String[0]));
		message.setSubject(email.getSubject());
		message.setText(email.getMessageContext());

		emailSender.send(message);
	}
}