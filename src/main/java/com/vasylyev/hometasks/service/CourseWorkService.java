package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.dto.CourseWorkDto;

import java.util.List;

public interface CourseWorkService {

    CourseWorkDto addCourseWork(CourseWorkDto courseWorkDto);

    void addCourseWorks(List<CourseWorkDto> courseWorkDtoList);

    CourseWorkDto findById(String id);

    List<CourseWorkDto> findAll();

    List<CourseWorkDto> findByCourseId(String courseId);

    List<CourseWorkDto> findByIds(Iterable<String> idList);

    List<CourseWorkDto> fillCourseById(List<CourseWorkDto> courseWorkDtoList);

    void deleteCourseWork(String courseWorkId);
}
