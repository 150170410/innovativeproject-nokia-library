package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final SendEmailService sendEmailService;

	@Async
	public void sendSimpleMessage(Email email, List<String> recipients) {

		if(recipients.size()  > 0) return;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipients.toArray(new String[0]));
		message.setSubject(email.getSubject());
		message.setText(email.getMessageContext());
		try {
			sendEmailService.send(message);
		}
		catch(MailException e){
			// attempts to send an email failed
			// we skip the problem
		}
	}
}