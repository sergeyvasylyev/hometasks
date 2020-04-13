package com.vasylyev.hometasks.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials_gs.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleSheetsService.class.getResourceAsStream("/" + credentialsFileName);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + "/" + credentialsFileName);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokenDir)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public void appendRow(CourseWorkDto courseWorkDto) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
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
