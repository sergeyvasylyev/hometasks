package com.vasylyev.hometasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class StudentSubmissionDto {

    private String id;
    private String alternateLink;
    private CourseDto course;
    private CourseWorkDto courseWork;
    private String courseId;
    private String courseWorkId;

    private double assignedGrade;
    private String assignmentSubmission;
    private String courseWorkType;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime creationTime;
    private String state;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updateTime;
    private String userId;

}
