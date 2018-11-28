package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.EmailDTO;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import com.sun.mail.util.MailConnectException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender emailSender;

	private final RetryTemplate retryTemplate;

	public MessageInfo sendSimpleMessage(EmailDTO emailDTO) {
		try {
			retryTemplate.execute(context -> {

				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(emailDTO.getRecipients().toArray(new String[0]));
				message.setSubject(emailDTO.getSubject());
				message.setText(emailDTO.getMessageContext());

				emailSender.send(message);

				return new MessageInfo(true, "success", Arrays.asList("Email sent successfully"));
			});
		}catch (MailException exc) {
			return new MessageInfo(false, exc.toString(), Arrays.asList("Failed to send messages"));
		}
		return new MessageInfo(true, "success", Arrays.asList("Email sent successfully"));
	}
}