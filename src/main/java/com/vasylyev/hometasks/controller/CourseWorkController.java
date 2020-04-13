package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.google.ClassroomService;
import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.service.CourseWorkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@AllArgsConstructor
public class CourseWorkController {

    private final CourseWorkService courseWorkService;
    private final ClassroomService classroomService;

    @RequestMapping(value = "/courseWork", method = RequestMethod.GET)
    public List<CourseWorkDto> getAllCourseWorks() {
        return courseWorkService.findAll();
    }

    @RequestMapping(value = "/courseWork", method = RequestMethod.POST)
    public CourseWorkDto saveCourseWorks() throws IOException, GeneralSecurityException {
        courseWorkService.addCourseWorks(classroomService.getCourseWork());
        return null;
    }
}
