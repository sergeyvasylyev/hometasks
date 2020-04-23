package com.vasylyev.hometasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CourseWorkDto {
    private String id;
    private String alternateLink;
    private CourseDto course;
    private String courseId;
    private LocalDateTime creationTime;
    private String description;
    private LocalDateTime dueDate;
    private double maxPoints;
    private String state;
    private String title;
    private LocalDateTime updateTime;
    private String workType;
    private String topicId;
}
