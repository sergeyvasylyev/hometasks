package com.vasylyev.hometasks.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.common.collect.ImmutableList;
import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.service.AppSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleSheetsService {

    private final AppSettingsService appSettingsService;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = ImmutableList.of(
            SheetsScopes.SPREADSHEETS_READONLY,
            SheetsScopes.SPREADSHEETS);

    public void appendRow(CourseWorkDto courseWorkDto) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(httpTransport, JSON_FACTORY, GoogleApiUtil.getCredentials(
                httpTransport,
                appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_SHEETS_TOKEN_DIR),
                appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_APP_CREDENTIALS),
                SCOPES,
                "ClassroomService"))
                .setApplicationName(appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_APP_NAME))
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
