package com.vasylyev.hometasks.google;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.service.AccountService;
import com.vasylyev.hometasks.service.AppSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleSheetsService {

    private final AppSettingsService appSettingsService;
    private final AccountService accountService;
    private final GoogleAuthorizationCodeFlow googleAuth;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public void appendRow(CourseWorkDto courseWorkDto) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Sheets service = new Sheets.Builder(httpTransport,
                JSON_FACTORY,
                new AuthorizationCodeInstalledApp(googleAuth, receiver).authorize(accountService.getDefaultAccount().getName())
        ).setApplicationName(appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_APP_NAME))
                .build();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM");

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(
                                courseWorkDto.getCreationTime().format(dateFormat),
                                nonNull(courseWorkDto.getDueDate()) ? courseWorkDto.getDueDate().format(dateFormat) : "",
                                "",
                                courseWorkDto.getCourse().getName(),
                                "Школа",
                                courseWorkDto.getTitle(),
                                courseWorkDto.getAlternateLink()
                        )));
        AppendValuesResponse appendResult = service.spreadsheets().values()
                .append(appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_SHEETS_SPREADSHEET_ID), "A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        log.info("Appended row: " + appendResult.getUpdates());
    }
}
