package com.vasylyev.hometasks.mapper;

import com.google.api.services.classroom.model.CourseWork;
import com.vasylyev.hometasks.dto.CourseWorkDto;
import com.vasylyev.hometasks.model.CourseWorkModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@AllArgsConstructor
public class CourseWorkMapper {

    private final CourseMapper courseMapper;

    public CourseWorkModel toModel(CourseWorkDto courseWorkDto) {
        return CourseWorkModel.builder()
                .alternateLink(courseWorkDto.getAlternateLink())
                .course(courseMapper.toModel(courseWorkDto.getCourse()))
                .courseId(courseWorkDto.getCourseId())
                .creationTime(courseWorkDto.getCreationTime())
                .description(courseWorkDto.getDescription())
                .dueDate(courseWorkDto.getDueDate())
                .id(courseWorkDto.getId())
                .maxPoints(courseWorkDto.getMaxPoints())
                .state(courseWorkDto.getState())
                .title(courseWorkDto.getTitle())
                .topicId(courseWorkDto.getTopicId())
                .updateTime(courseWorkDto.getUpdateTime())
                .workType(courseWorkDto.getWorkType())
                .build();
    }

    public CourseWorkDto toDto(CourseWorkModel courseWorkModel) {
        return CourseWorkDto.builder()
                .alternateLink(courseWorkModel.getAlternateLink())
                .course(courseMapper.toDto(courseWorkModel.getCourse()))
                .courseId(courseWorkModel.getCourseId())
                .creationTime(courseWorkModel.getCreationTime())
                .description(courseWorkModel.getDescription())
                .dueDate(courseWorkModel.getDueDate())
                .id(courseWorkModel.getId())
                .maxPoints(courseWorkModel.getMaxPoints())
                .state(courseWorkModel.getState())
                .title(courseWorkModel.getTitle())
                .topicId(courseWorkModel.getTopicId())
                .updateTime(courseWorkModel.getUpdateTime())
                .workType(courseWorkModel.getWorkType())
                .build();
    }

    public CourseWorkDto toDto(CourseWork courseWork) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime dueDate = null;
        int hours = nonNull(courseWork.getDueTime()) ? nonNull(courseWork.getDueTime().getHours()) ? courseWork.getDueTime().getHours() : 0 : 0;
        int minutes = nonNull(courseWork.getDueTime()) ? nonNull(courseWork.getDueTime().getMinutes()) ? courseWork.getDueTime().getMinutes() : 0 : 0;
        if (nonNull(courseWork.getDueDate())) {
            dueDate = LocalDateTime.of(courseWork.getDueDate().getYear()
                    , courseWork.getDueDate().getMonth()
                    , courseWork.getDueDate().getDay()
                    , hours
                    , minutes);
        }
        double maxPoints = nonNull(courseWork.getMaxPoints()) ? courseWork.getMaxPoints() : 0;

        return CourseWorkDto.builder()
                .alternateLink(courseWork.getAlternateLink())
                .courseId(courseWork.getCourseId())
                .creationTime(LocalDateTime.parse(courseWork.getCreationTime(), formatter))
                .description(courseWork.getDescription())
                .dueDate(dueDate)
                .id(courseWork.getId())
                .maxPoints(maxPoints)
                .state(courseWork.getState())
                .updateTime(LocalDateTime.parse(courseWork.getUpdateTime(), formatter))
                .title(courseWork.getTitle())
                .workType(courseWork.getWorkType())
                .build();
    }
}
