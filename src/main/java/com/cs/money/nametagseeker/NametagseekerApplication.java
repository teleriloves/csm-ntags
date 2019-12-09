package com.cs.money.nametagseeker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class NametagseekerApplication {
	
	@Autowired static TelegramBot bot;
	
	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(NametagseekerApplication.class, args);
	}
}
