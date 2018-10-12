package com.nokia.library.nokiainnovativeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NokiaInnovativeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NokiaInnovativeProjectApplication.class, args);
	}
}
