package com.nokia.library.nokiainnovativeproject.config.profiles;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile("development")
public class DevelopmentConfiguration {
	@PostConstruct
	public void info(){
		System.out.println("Loaded development configuration!");
	}
}
