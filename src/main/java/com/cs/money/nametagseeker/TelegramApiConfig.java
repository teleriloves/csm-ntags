package com.cs.money.nametagseeker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@Configuration
public class TelegramApiConfig {

	@Bean
	public TelegramBotsApi telegramBotsApi() {
		return new TelegramBotsApi();
	}
	
}
