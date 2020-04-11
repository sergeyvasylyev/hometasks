package com.vasylyev.hometasks.config;

import com.vasylyev.hometasks.telegram.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
public class BaseConfig {

    @Bean
    public TelegramBot configBot() {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        TelegramBot bot = new TelegramBot();
        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return bot;
    }
}
