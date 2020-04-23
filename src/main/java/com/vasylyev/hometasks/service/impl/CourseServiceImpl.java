package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.dto.CourseDto;
import com.vasylyev.hometasks.exception.ElementNotFoundException;
import com.vasylyev.hometasks.mapper.CourseMapper;
import com.vasylyev.hometasks.model.CourseModel;
import com.vasylyev.hometasks.repository.CourseRepository;
import com.vasylyev.hometasks.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;

    @Override
    public CourseDto addCourse(CourseDto courseDto) {
        CourseModel courseModel = courseRepository.save(courseMapper.toModel(courseDto));
        log.info("Course saved. id:" + courseModel.getId());
        return courseMapper.toDto(courseModel);
    }

    @Override
    public void addCourses(List<CourseDto> courseDtoList) {
        List<String> courseIds = courseDtoList.stream()
                .map(c -> c.getId())
                .collect(Collectors.toList());
        List<CourseModel> courseModelList = courseRepository.findAllById(courseIds);
        for (CourseDto courseDto : courseDtoList) {
            if (isNull(courseModelList.stream().filter(c -> c.getId().equals(courseDto.getId())).findFirst().orElse(null))) {
                courseRepository.save(courseMapper.toModel(courseDto));
                log.info("Course saved. id:" + courseDto.getId());
            } //else {
                //log.info("Course already exist. id:" + courseDto.getId());
            //}
        }
    }

    @Override
    public CourseDto findById(String id) {
        return courseMapper.toDto(courseRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Course not found. id:" + id)));
    }

    @Override
    public List<CourseDto> findAll() {
        return courseRepository.findAll().stream()
                .map(c -> courseMapper.toDto(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllNames() {
        List<String> courseNames = new ArrayList<>();
        findAll().stream()
                .forEach(c -> courseNames.add(c.getName()));
        return courseNames;
    }

    @Override
    public List<CourseDto> findByIds(Iterable<String> idList) {
        return courseRepository.findAllById(idList).stream()
                .map(c -> courseMapper.toDto(c))
                .collect(Collectors.toList());
    }
}
