package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.scheduler.GetHomeTasksScheduler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/settings")
public class SettingsController {

    private final GetHomeTasksScheduler homeTasksScheduler;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getSettings() {
        ModelAndView modelSettings = new ModelAndView();
        modelSettings.setViewName("settings");
        return modelSettings;
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
}
