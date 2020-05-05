package com.vasylyev.hometasks.mapper;

import com.google.api.services.classroom.model.Course;
import com.vasylyev.hometasks.dto.AccountDto;
import com.vasylyev.hometasks.dto.CourseDto;
import com.vasylyev.hometasks.model.CourseModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseMapper {

    private final AccountMapper accountMapper;

    public CourseModel toModel(CourseDto courseDto) {
        return CourseModel.builder()
                .alternateLink(courseDto.getAlternateLink())
                .courseState(courseDto.getCourseState())
                .descriptionHeading(courseDto.getDescriptionHeading())
                .enrollmentCode(courseDto.getEnrollmentCode())
                .id(courseDto.getId())
                .name(courseDto.getName())
                .room(courseDto.getRoom())
                .section(courseDto.getSection())
                .account(accountMapper.toModel(courseDto.getAccount()))
                .build();
    }

    public CourseDto toDto(CourseModel courseModel) {
        return CourseDto.builder()
                .alternateLink(courseModel.getAlternateLink())
                .courseState(courseModel.getCourseState())
                .descriptionHeading(courseModel.getDescriptionHeading())
                .enrollmentCode(courseModel.getEnrollmentCode())
                .id(courseModel.getId())
                .name(courseModel.getName())
                .room(courseModel.getRoom())
                .section(courseModel.getSection())
                .account(accountMapper.toDto(courseModel.getAccount()))
                .build();
    }

    public CourseDto toDto(Course course, AccountDto accountDto) {
        return CourseDto.builder()
                .alternateLink(course.getAlternateLink())
                .courseState(course.getCourseState())
                .descriptionHeading(course.getDescriptionHeading())
                .enrollmentCode(course.getEnrollmentCode())
                .id(course.getId())
                .name(course.getName())
                .room(course.getRoom())
                .section(course.getSection())
                .account(accountDto)
                .build();
    }
}
