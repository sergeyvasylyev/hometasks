package com.vasylyev.hometasks.scheduler;

import com.vasylyev.hometasks.classroom.ClassroomService;
import com.vasylyev.hometasks.service.CourseService;
import com.vasylyev.hometasks.service.CourseWorkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Component
@AllArgsConstructor
public class GetHomeTasksScheduler {

    private final CourseService courseService;
    private final CourseWorkService courseWorkService;
    private final ClassroomService classroomService;

    @Scheduled(fixedRateString = "${hometask.get.job.frequency}")
    public void getHometaskJob() throws IOException, GeneralSecurityException {
        log.info("Get hometask job started");
        courseService.addCourses(classroomService.getCourses());
        courseWorkService.addCourseWorks(classroomService.getCourseWork());
    }
    //TODO: add new row at GoogleDoc for new hometask
}