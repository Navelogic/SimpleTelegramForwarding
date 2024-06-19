package com.telegram.simpletelegramforwarding.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public TelegramBot telegramBot(
			@Value("${telegram.bot.token}") String botToken,
			@Value("${telegram.bot.testChannelId}") String botTestChannelId,
			@Value("${telegram.bot.prodChannelId}") String botProdChannelId,
			@Value("${telegram.bot.username}") String botUsername,
			@Value("${telegram.bot.name}") String botName,
			@Value("${telegram.bot.developerId}") String developerId){

		TelegramBot bot = new TelegramBot(botToken, botName, botUsername, developerId, botTestChannelId, botProdChannelId);
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(bot);
		} catch (TelegramApiException e){
			e.printStackTrace();
		}
		return bot;
	}
}