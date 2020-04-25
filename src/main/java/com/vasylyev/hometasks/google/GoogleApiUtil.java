package com.vasylyev.hometasks.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class GoogleApiUtil {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Creates an authorized Credential object.
     *
     * @param httpTransport The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials_gc.json file cannot be found.
     */
    public static Credential getCredentials(final NetHttpTransport httpTransport,
                                            String tokenDir, String credentialsFileName, List<String> scopes, String apiType) throws IOException {

        // Load client secrets.
        InputStream in = null;
        if (apiType.equals("ClassroomService")) {
            in = ClassroomService.class.getResourceAsStream("/" + credentialsFileName);
        } else {
            in = GoogleSheetsService.class.getResourceAsStream("/" + credentialsFileName);
        }
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + "/" + credentialsFileName);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        //setDataStoreFactory(new FileDataStoreFactory(new ClassPathResource("tokens/"+tokenDir).getFile()))

        InputStream inputStream = new ClassPathResource("tokens/" + tokenDir).getInputStream();
        java.io.File tokenFile = java.io.File.createTempFile("tmp", "");
        java.io.File tokenFile2 = new ClassPathResource("tokens/"+tokenDir).getFile();
        try {
            FileUtils.copyInputStreamToFile(inputStream, tokenFile);
//            java.nio.file.Files.copy(
//                    inputStream,
//                    tokenFile.toPath(),
//                    StandardCopyOption.REPLACE_EXISTING);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(tokenFile);
        //FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(new ClassPathResource("tokens/" + tokenDir).getFile());

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = null;
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets, scopes)
                    .setDataStoreFactory(dataStoreFactory)
                    .setAccessType("offline")
                    .build();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

}
