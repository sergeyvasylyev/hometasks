package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.dto.CourseWorkDto;

import java.util.List;

public interface CourseWorkService {

    public CourseWorkDto addCourseWork(CourseWorkDto courseWorkDto);

    public void addCourseWorks(List<CourseWorkDto> courseWorkDtoList);

    public CourseWorkDto findById(String id);

    public List<CourseWorkDto> findAll();

    public List<CourseWorkDto> fillCourseById(List<CourseWorkDto> courseWorkDtoList);
}
