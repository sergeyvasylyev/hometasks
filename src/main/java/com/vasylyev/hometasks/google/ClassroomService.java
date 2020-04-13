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
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.ListCourseWorkResponse;
import com.google.api.services.classroom.model.ListCoursesResponse;
import com.google.common.collect.ImmutableList;
import com.vasylyev.hometasks.dto.CourseDto;
import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.exception.ElementNotFoundException;
import com.vasylyev.hometasks.mapper.CourseMapper;
import com.vasylyev.hometasks.mapper.CourseWorkMapper;
import com.vasylyev.hometasks.service.CourseWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClassroomService {

    private final CourseMapper courseMapper;
    private final CourseWorkMapper courseWorkMapper;
    private final CourseWorkService courseWorkService;

    @Value("${google.app.name}")
    private String appName;

    @Value("${google.classroom.token.dir}")
    private String tokenDir;

    @Value("${google.classroom.token.credentials}")
    private String credentialsFileName;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = ImmutableList.of(
            ClassroomScopes.CLASSROOM_COURSES_READONLY
            , ClassroomScopes.CLASSROOM_COURSEWORK_ME_READONLY
            , "https://www.googleapis.com/auth/classroom.topics.readonly"
    );

    public List<CourseDto> getCourses() throws IOException, GeneralSecurityException {
        Classroom service = getClassroom();
        ListCoursesResponse response = service.courses().list().execute();

        List<Course> courses = response.getCourses();
        if (courses == null || courses.size() == 0) {
            throw new ElementNotFoundException("Courses not found");
        }
        return courses.stream()
                .map(c -> courseMapper.toDto(c))
                .collect(Collectors.toList());
    }

    public List<CourseWorkDto> getCourseWork() throws IOException, GeneralSecurityException {
        Classroom service = getClassroom();
        ListCoursesResponse response = service.courses().list().execute();

        List<CourseWorkDto> courseWorkDtoList = new ArrayList<>();
        for (Course course : response.getCourses()) {
            ListCourseWorkResponse courseWorkResponse = service.courses().courseWork().list(course.getId()).execute();
            courseWorkResponse.getCourseWork().stream()
                    .forEach(cw -> courseWorkDtoList.add(courseWorkMapper.toDto(cw)));
        }

        return courseWorkService.fillCourseById(courseWorkDtoList);
    }

    private Classroom getClassroom() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Classroom.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(appName)
                .build();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials_gc.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = ClassroomService.class.getResourceAsStream("/" + credentialsFileName);
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
    //TODO: move authorization to UI
}