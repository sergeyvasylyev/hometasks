package com.vasylyev.hometasks.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.common.collect.ImmutableList;
import com.vasylyev.hometasks.google.JPADataStoreFactory;
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.repository.GoogleCredentialRepository;
import com.vasylyev.hometasks.service.AppSettingsService;
import com.vasylyev.hometasks.telegram.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

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

    @Bean
    public GoogleAuthorizationCodeFlow googleAuth(GoogleCredentialRepository repository,
                                                  AppSettingsService appSettingsService) throws IOException {

        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        Resource resource = new ClassPathResource(appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_APP_CREDENTIALS));
        InputStream in = resource.getInputStream();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY,
                new InputStreamReader(in)
        );
        Collection<String> scopes = ImmutableList.of(
                ClassroomScopes.CLASSROOM_COURSES_READONLY,
                ClassroomScopes.CLASSROOM_COURSEWORK_ME_READONLY,
                "https://www.googleapis.com/auth/classroom.topics.readonly"
        );

        DataStoreFactory dataStore = new JPADataStoreFactory(repository);

        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                JSON_FACTORY,
                clientSecrets,
                scopes
        )
                .setAccessType("offline")
                .setDataStoreFactory(dataStore)
                .build();
    }
}
