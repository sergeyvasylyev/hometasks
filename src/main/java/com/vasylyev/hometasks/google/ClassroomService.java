package com.vasylyev.hometasks.google;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
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
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.service.AccountService;
import com.vasylyev.hometasks.service.AppSettingsService;
import com.vasylyev.hometasks.service.CourseWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassroomService {

    private final CourseMapper courseMapper;
    private final CourseWorkMapper courseWorkMapper;
    private final CourseWorkService courseWorkService;
    private final AppSettingsService appSettingsService;
    private final AccountService accountService;
    private final GoogleAuthorizationCodeFlow googleAuth;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

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
            if (nonNull(courseWorkResponse) && !courseWorkResponse.isEmpty()) {
                courseWorkResponse.getCourseWork().stream()
                        .forEach(cw -> courseWorkDtoList.add(courseWorkMapper.toDto(cw)));
            }
        }

        return courseWorkService.fillCourseById(courseWorkDtoList);
    }

    private Classroom getClassroom() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new Classroom.Builder(httpTransport,
                JSON_FACTORY,
                new AuthorizationCodeInstalledApp(googleAuth, receiver).authorize(accountService.getDefaultAccount().getName())
        ).setApplicationName(appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_APP_NAME))
                .build();
//        return new Classroom.Builder(httpTransport, JSON_FACTORY, new GoogleApiUtil().getCredentials(
//                httpTransport,
//                appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_CLASSROOM_TOKEN_DIR),
//                //appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_APP_CREDENTIALS),
//                SCOPES))
//                .setApplicationName(appSettingsService.getSettingDataForDefaultAccount(SettingType.GOOGLE_APP_NAME))
//                .build();
    }
}