package com.vasylyev.hometasks.scheduler;

import com.vasylyev.hometasks.google.ClassroomService;
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.scheduler.model.JobHistory;
import com.vasylyev.hometasks.service.AppSettingsService;
import com.vasylyev.hometasks.service.CourseService;
import com.vasylyev.hometasks.service.CourseWorkService;
import com.vasylyev.hometasks.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetHomeTasksScheduler {

    private final CourseService courseService;
    private final CourseWorkService courseWorkService;
    private final ClassroomService classroomService;
    private final AppSettingsService appSettingsService;
    private final JobHistoryService jobHistoryService;

    //@Scheduled(fixedRateString = "${hometask.get.job.frequency}")
    @Scheduled(fixedRateString = "300000")
    public void getHometaskJob() throws IOException, GeneralSecurityException {
        if (appSettingsService.getSettingDataForDefaultAccount(SettingType.JOB_GET_COURSES_STATUS).equals("active")) {
            setJobHistoryStatus("Started");
            log.info("Get hometask job started");

            courseService.addCourses(classroomService.getCourses());
            courseWorkService.addCourseWorks(classroomService.getCourseWork());

            log.info("Get hometask job ended");
            setJobHistoryStatus("Ended");
        }
    }

    private void setJobHistoryStatus(String jobHistoryStatus){
        if (appSettingsService.getSettingDataForDefaultAccount(SettingType.JOB_GET_COURSES_HISTORY_STATUS).equals("active")) {
            jobHistoryService.saveJobHistory(JobHistory.builder()
                    .name(SettingType.JOB_GET_COURSES_STATUS.toString())
                    .status(jobHistoryStatus)
                    .executeDate(LocalDateTime.now())
                    .build());
        }
    }
}
