package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.dto.CourseDto;

import java.util.List;

public interface CourseService {

    CourseDto addCourse(CourseDto courseDto);

    void addCourses(List<CourseDto> courseDtoList);

    CourseDto findById(String id);

    List<CourseDto> findAll();

    List<String> findAllNames();

    List<CourseDto> findByIds(Iterable<String> idList);
}
