package com.vasylyev.hometasks.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.vasylyev.hometasks.repository.GoogleCredentialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

@Slf4j
public class GoogleApiUtil {

    @Autowired
    private GoogleCredentialRepository repository;

    @Autowired
    private GoogleAuthorizationCodeFlow googleAuth;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Creates an authorized Credential object.
     *
     * @param httpTransport The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials_gc.json file cannot be found.
     */
    public Credential getCredentials(final NetHttpTransport httpTransport,
                                            String credentialsFileName, List<String> scopes) throws IOException {

        // Load client secrets.
        InputStream in = getClass().getResourceAsStream("/" + credentialsFileName);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + "/" + credentialsFileName);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        DataStoreFactory dataStoreFactory = new JPADataStoreFactory(repository);

        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = null;
//        try {
//            flow = new GoogleAuthorizationCodeFlow.Builder(
//                    httpTransport, JSON_FACTORY, clientSecrets, scopes)
//                    .setDataStoreFactory(dataStoreFactory)
//                    //.setDataStoreFactory(new FileDataStoreFactory(fileToken))
//                    //.setDataStoreFactory(new FileDataStoreFactory(new File(new ClassPathResource(tokenDir).getURI())))
//                    //.setDataStoreFactory(new FileDataStoreFactory(new File(ClassroomService.class.getResource("/" + tokenDir).getPath())))
//                    .setAccessType("offline")
//                    .build();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new AuthorizationCodeInstalledApp(googleAuth, receiver).authorize("user");
    }
}
