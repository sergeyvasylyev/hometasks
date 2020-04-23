package com.vasylyev.hometasks.config;

import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.service.AppSettingsService;
import com.vasylyev.hometasks.telegram.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
public class BaseConfig {

    @Bean
    public TelegramBot configBot(AppSettingsService appSettingsService) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        TelegramBot bot = new TelegramBot() {
            @Override
            public String getBotToken() {
                return appSettingsService.getSettingDataForDefaultAccount(SettingType.TELEGRAM_BOT_TOKEN);
            }

            @Override
            public String getBotUsername() {
                return appSettingsService.getSettingDataForDefaultAccount(SettingType.TELEGRAM_BOT_USERNAME);
            }
        };
        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return bot;
    }
}
