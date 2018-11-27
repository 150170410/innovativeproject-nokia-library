package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.EmailDTO;
import com.nokia.library.nokiainnovativeproject.beans.EmailSender;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender emailSender;
	private final Environment environment;

	public MessageInfo sendSimpleMessage(EmailDTO emailDTO) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(emailDTO.getRecipients().toArray(new String[0]));
			message.setSubject(emailDTO.getSubject());
			message.setText(emailDTO.getMessageContext());

			emailSender.send(message);
		} catch (MailException exception) {
			exception.printStackTrace();
			return new MessageInfo(false, exception.toString(), Arrays.asList("Failed to send messages"));
		}
		return new MessageInfo(true, "success", Arrays.asList("Email sent successfully"));
	}

}