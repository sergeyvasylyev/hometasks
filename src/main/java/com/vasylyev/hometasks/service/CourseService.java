package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.dto.CourseDto;

import java.util.List;

public interface CourseService {

    public CourseDto addCourse(CourseDto courseDto);

    public void addCourses(List<CourseDto> courseDtoList);

    public CourseDto findById(String id);

    public List<CourseDto> findAll();

    public List<CourseDto> findByIds(Iterable<String> idList);
}
