package com.vasylyev.hometasks.scheduler;

import com.vasylyev.hometasks.google.ClassroomService;
import com.vasylyev.hometasks.model.AppSettings;
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.service.AppSettingsService;
import com.vasylyev.hometasks.service.CourseService;
import com.vasylyev.hometasks.service.CourseWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetHomeTasksScheduler {

    private final CourseService courseService;
    private final CourseWorkService courseWorkService;
    private final ClassroomService classroomService;
    private final AppSettingsService appSettingsService;

    //@Scheduled(fixedRateString = "${hometask.get.job.frequency}")
    @Scheduled(fixedRateString = "300000")
    public void getHometaskJob() throws IOException, GeneralSecurityException {
        if (appSettingsService.getSettingDataForDefaultAccount(SettingType.JOB_GET_COURSES_STATUS).equals("active")){
            log.info("Get hometask job started");
            courseService.addCourses(classroomService.getCourses());
            courseWorkService.addCourseWorks(classroomService.getCourseWork());
            log.info("Get hometask job ended");
        }
    }
}
