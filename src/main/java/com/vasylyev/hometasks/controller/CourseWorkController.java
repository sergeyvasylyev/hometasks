package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.google.ClassroomService;
import com.vasylyev.hometasks.service.CourseWorkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        return courseWorkService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public CourseWorkDto saveCourseWorks() throws IOException, GeneralSecurityException {
        courseWorkService.addCourseWorks(classroomService.getCourseWork());
        return null;
    }

    @RequestMapping(value = "/{courseworkId}", method = RequestMethod.GET)
    public CourseWorkDto getCoursework(@PathVariable String courseworkId) {
        return courseWorkService.findById(courseworkId);
    }

    @RequestMapping(value = "/{courseworkId}", method = RequestMethod.PUT)
    public CourseWorkDto updateCoursework(@PathVariable String courseworkId,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                          @ModelAttribute("courseworkDto") CourseWorkDto courseworkDto) {
        return courseWorkService.addCourseWork(courseworkDto);
    }

    @RequestMapping(value = "/{courseworkId}", method = RequestMethod.DELETE)
    public String deleteCoursework(@PathVariable String courseworkId) {
        courseWorkService.deleteCourseWork(courseworkId);
        return "ok";
    }
}
