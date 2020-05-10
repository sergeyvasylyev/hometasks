package com.vasylyev.hometasks.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.common.collect.ImmutableList;
import com.vasylyev.hometasks.google.JPADataStoreFactory;
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.repository.GoogleCredentialRepository;
import com.vasylyev.hometasks.service.AppSettingsService;
import com.vasylyev.hometasks.telegram.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

@Slf4j
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

        final String classroomTopicReadonly = "https://www.googleapis.com/auth/classroom.topics.readonly";
        final String classroomStudentSubmission = "https://www.googleapis.com/auth/classroom.coursework.me.readonly";
        final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        String appCredentials = appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_APP_CREDENTIALS);
        if (appCredentials.isEmpty()) {
            appCredentials = "credentials_gc.json";
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                jsonFactory,
                new InputStreamReader(new ClassPathResource(appCredentials).getInputStream())
        );
        Collection<String> scopes = ImmutableList.of(
                ClassroomScopes.CLASSROOM_COURSES_READONLY,
                ClassroomScopes.CLASSROOM_COURSEWORK_ME_READONLY,
                classroomTopicReadonly,
                classroomStudentSubmission,
                SheetsScopes.SPREADSHEETS_READONLY,
                SheetsScopes.SPREADSHEETS
        );
        DataStoreFactory dataStore = new JPADataStoreFactory(repository);

        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                jsonFactory,
                clientSecrets,
                scopes
        )
                .setAccessType("offline")
                .setDataStoreFactory(dataStore)
                .build();
    }
}
