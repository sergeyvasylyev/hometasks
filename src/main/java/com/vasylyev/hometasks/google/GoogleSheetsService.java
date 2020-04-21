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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequestMapping
public class GoogleSheetsService {

    @Value("${google.app.name}")
    private String appName;

    @Value("${google.sheets.token.dir}")
    private String tokenDir;

    @Value("${google.sheets.token.credentials}")
    private String credentialsFileName;

    @Value("${google.sheets.spreadsheet.id}")
    private String spreadsheetId;

    @Value("${google.sheets.spreadsheet.range}")
    private String spreadsheetRange;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = ImmutableList.of(
            SheetsScopes.SPREADSHEETS_READONLY
            , SheetsScopes.SPREADSHEETS);

    public void appendRow(CourseWorkDto courseWorkDto) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleApiUtil.getCredentials(HTTP_TRANSPORT, tokenDir, credentialsFileName, SCOPES, "ClassroomService"))
                .setApplicationName(appName)
                .build();

        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd.MM");

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(
                                courseWorkDto.getCreationTime().format(DateFormat)
                                , nonNull(courseWorkDto.getDueDate()) ? courseWorkDto.getDueDate().format(DateFormat) : ""
                                , ""
                                , courseWorkDto.getCourse().getName()
                                , "Школа"
                                , courseWorkDto.getTitle()
                                , courseWorkDto.getAlternateLink()
                        )));
        AppendValuesResponse appendResult = service.spreadsheets().values()
                .append(spreadsheetId, "A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        log.info("Appended row: " + appendResult.getUpdates());
    }
}
