package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.classroom.ClassroomService;
import com.vasylyev.hometasks.dto.CourseDto;
import com.vasylyev.hometasks.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ClassroomService classroomService;

    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public List<CourseDto> getAllCourses() {
        return courseService.findAll();
    }

    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public CourseDto saveCourses() throws IOException, GeneralSecurityException {
        courseService.addCourses(classroomService.getCourses());
        return null;
    }
}
