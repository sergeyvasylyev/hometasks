package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.google.ClassroomService;
import com.vasylyev.hometasks.service.CourseWorkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/coursework")
public class CourseWorkController {

    private final CourseWorkService courseWorkService;
    private final ClassroomService classroomService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getCourseWork() {
        ModelAndView modelCoursework = new ModelAndView();
        modelCoursework.setViewName("coursework");
        return modelCoursework;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CourseWorkDto> getAllCourseWorks() {
        log.info("coursework all. get");
        return courseWorkService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public CourseWorkDto saveCourseWorks() throws IOException, GeneralSecurityException {
        courseWorkService.addCourseWorks(classroomService.getCourseWork());
        return null;
    }
}
