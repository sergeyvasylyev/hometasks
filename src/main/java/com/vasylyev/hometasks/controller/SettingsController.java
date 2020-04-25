package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.dto.AccountDto;
import com.vasylyev.hometasks.scheduler.GetHomeTasksScheduler;
import com.vasylyev.hometasks.scheduler.model.JobHistory;
import com.vasylyev.hometasks.service.AccountService;
import com.vasylyev.hometasks.service.AppSettingsService;
import com.vasylyev.hometasks.service.JobHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/settings")
public class SettingsController {

    private final AppSettingsService appSettingsService;
    private final AccountService accountService;
    private final GetHomeTasksScheduler homeTasksScheduler;
    private final JobHistoryService jobHistoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getSettings() {
        ModelAndView modelSettings = new ModelAndView();
        modelSettings.addObject("appSettings", appSettingsService.findAll());
        modelSettings.setViewName("settings");
        return modelSettings;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateSettings(@RequestBody AccountDto accountDto) {
        accountService.addAccount(accountDto);
    }

    @RequestMapping(value = "/getDefault", method = RequestMethod.GET)
    public AccountDto getDefaultAccount() {
        return accountService.getDefaultAccount();
    }

    @RequestMapping(value = "/force", method = RequestMethod.GET)
    public void forceJob() {
        log.info("Force get hometask job.");
        try {
            homeTasksScheduler.getHometaskJob();
        } catch (IOException e) {
            log.error("Job force error. " + e.getMessage());
        } catch (GeneralSecurityException e) {
            log.error("Job force error. " + e.getMessage());
        }
    }

    @RequestMapping(value = "/job", method = RequestMethod.GET)
    public ModelAndView getJobSettings() {
        ModelAndView modelSettings = new ModelAndView();
        modelSettings.setViewName("job");
        return modelSettings;
    }

    @RequestMapping(value = "/jobHistory", method = RequestMethod.GET)
    public List<JobHistory> getJobHistory(){
        return jobHistoryService.getAllJobHistory();
    }
}
