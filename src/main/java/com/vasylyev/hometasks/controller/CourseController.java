package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.dto.CourseDto;
import com.vasylyev.hometasks.google.ClassroomService;
import com.vasylyev.hometasks.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/course")
public class CourseController {

    private final CourseService courseService;
    private final ClassroomService classroomService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getCourse() {
        ModelAndView modelCourse = new ModelAndView();
        modelCourse.setViewName("course");
        return modelCourse;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CourseDto> getAllCourses() {
        return courseService.findAll();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public List<CourseDto> updateCourses() throws IOException, GeneralSecurityException {
        courseService.addCourses(classroomService.getCourses());
        log.info("Update course by user request.");
        return courseService.findAll();
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public CourseDto getCourse(@PathVariable String courseId) {
        return courseService.findById(courseId);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.PUT)
    public CourseDto updateCourse(@PathVariable String courseId, @ModelAttribute("courseDto") CourseDto courseDto) {
        return courseService.addCourse(courseDto);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.DELETE)
    public String deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse(courseId);
        return "ok";
    }
}
