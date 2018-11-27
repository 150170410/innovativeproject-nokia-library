package com.nokia.library.nokiainnovativeproject.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailSender {

	@Bean
	public JavaMailSender javaMailSender(Environment environment) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(Integer.parseInt("spring.mail.port"));
		mailSender.setUsername(environment.getProperty("spring.mail.username"));
		mailSender.setPassword(environment.getProperty("spring.mail.password"));
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "spring.mail.properties.mail.transport.protocol");
		props.put("mail.smtp.auth", "spring.mail.properties.mail.smtp.auth");
		props.put("mail.smtp.starttls.enable", "spring.mail.properties.mail.starttls.enable");
		props.put("mail.debug", environment.getProperty("spring.mail.debug"));
		return mailSender;
	}
}
