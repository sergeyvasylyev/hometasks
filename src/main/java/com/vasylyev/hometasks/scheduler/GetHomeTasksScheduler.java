package com.vasylyev.hometasks.scheduler;

import com.vasylyev.hometasks.dto.AccountDto;
import com.vasylyev.hometasks.dto.AppSettingsDto;
import com.vasylyev.hometasks.google.ClassroomService;
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.scheduler.model.JobHistory;
import com.vasylyev.hometasks.service.AccountService;
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
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetHomeTasksScheduler {

    private final CourseService courseService;
    private final CourseWorkService courseWorkService;
    private final ClassroomService classroomService;
    private final AccountService accountService;
    private final AppSettingsService appSettingsService;
    private final JobHistoryService jobHistoryService;

    @Scheduled(fixedRateString = "600000")
    public void getHometaskJob() throws IOException, GeneralSecurityException {

        List<AccountDto> accountDtoList = accountService.findAllActive();
        for (AccountDto accountDto : accountDtoList) {
            AppSettingsDto appSettingsDto = accountDto.getAppSettings().stream()
                    .filter(appS -> appS.getSettingType().equals(SettingType.JOB_GET_COURSES_STATUS))
                    .findFirst()
                    .orElse(null);
            if (nonNull(appSettingsDto) && appSettingsDto.getSettingData().equals("active")) {

                setJobHistoryStatus(accountDto, "Started");
                log.info("Get hometask job started. Account: " + accountDto.getName());

                courseService.addCourses(classroomService.getCourses(accountDto));
                courseWorkService.addCourseWorks(classroomService.getCourseWork(accountDto));

                log.info("Get hometask job ended. Account: " + accountDto.getName());
                setJobHistoryStatus(accountDto, "Ended");
            }
        }
    }

    private void setJobHistoryStatus(AccountDto accountDto, String jobHistoryStatus) {
        AppSettingsDto appSettingsDto = accountDto.getAppSettings().stream()
                .filter(appS -> appS.getSettingType().equals(SettingType.JOB_GET_COURSES_HISTORY_STATUS))
                .findFirst()
                .orElse(null);
        if (nonNull(appSettingsDto) && appSettingsDto.getSettingData().equals("active")) {
            jobHistoryService.saveJobHistory(JobHistory.builder()
                    .name(SettingType.JOB_GET_COURSES_STATUS.toString())
                    .status(jobHistoryStatus)
                    .executeDate(LocalDateTime.now())
                    .account(accountDto.getName())
                    .build());
        }
    }
}
