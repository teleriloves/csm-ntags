package com.cs.money.nametagseeker;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
	
	@Autowired ItemRetrievalService itemService;
	@Autowired TelegramBotsApi botsApi;
	
//	@Value("${bot.token}")
//	@Getter private String token;
	
//	@Value("${bot.username}")
//	@Getter private String username;
	
	@Override
	public String getBotUsername() {
		return "csmnametagbot";
	}

	@Override
	public String getBotToken() {
		return "1011105173:AAGqYCh6vJm29EvwIqmRERNr06td4tM28jU";
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			SendMessage response = update.getMessage().getText().equals("get")
				? generateItemsResponse(update) : generateErrorMessage(update); 
						
			try {
				execute(response);
			} catch (TelegramApiException e) {
				log.error("Failed to send message. Error -> {}", e.getMessage());
			}
		}
	}

	private SendMessage generateErrorMessage(Update update) {
		SendMessage response = new SendMessage();
		response.setChatId(update.getMessage().getChatId());
		response.setText("Wrong input. Please write \"get\" to list available items");
		return response;
	}

	private SendMessage generateItemsResponse(Update update) {
		Message message = update.getMessage();
		Long chatId = message.getChatId();
		SendMessage response = new SendMessage();
		List<String> items = itemService.seekFormatted();
		String text = Optional.ofNullable(items).map(List::toString).orElse("");
		response.setChatId(chatId);
		response.setText("Items found: " + text);
		log.info("Message \"{}\" to {}", text, chatId);
		return response;
	}

	@PostConstruct
	public void init() {
		try {
			botsApi.registerBot(this);
			log.info("Logged succesfully");
		} catch (TelegramApiRequestException e) {
			log.info("Error logging bot");
		}
	}
	
}