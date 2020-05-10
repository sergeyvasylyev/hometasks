package com.vasylyev.hometasks.mapper;

import com.google.api.services.classroom.model.StudentSubmission;
import com.vasylyev.hometasks.dto.StudentSubmissionDto;
import com.vasylyev.hometasks.model.StudentSubmissionModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class StudentSubmissionMapper {

    private final CourseWorkMapper courseWorkMapper;
    private final CourseMapper courseMapper;

    public StudentSubmissionModel toModel(StudentSubmissionDto studentSubmissionDto) {
        return StudentSubmissionModel.builder()
                .alternateLink(studentSubmissionDto.getAlternateLink())
                .assignedGrade(studentSubmissionDto.getAssignedGrade())
                //.assignmentSubmission(studentSubmissionDto.getAssignmentSubmission())
                .courseWorkType(studentSubmissionDto.getCourseWorkType())
                .id(studentSubmissionDto.getId())
                .userId(studentSubmissionDto.getUserId())
                .state(studentSubmissionDto.getState())
                .creationTime(studentSubmissionDto.getCreationTime())
                .updateTime(studentSubmissionDto.getUpdateTime())
                .course(courseMapper.toModel(studentSubmissionDto.getCourse()))
                .courseWork(courseWorkMapper.toModel(studentSubmissionDto.getCourseWork()))
                .build();
    }

    public StudentSubmissionDto toDto(StudentSubmissionModel studentSubmissionModel) {
        return StudentSubmissionDto.builder()
                .alternateLink(studentSubmissionModel.getAlternateLink())
                .assignedGrade(studentSubmissionModel.getAssignedGrade())
                //.assignmentSubmission(studentSubmissionModel.getAssignmentSubmission())
                .courseWorkType(studentSubmissionModel.getCourseWorkType())
                .id(studentSubmissionModel.getId())
                .userId(studentSubmissionModel.getUserId())
                .state(studentSubmissionModel.getState())
                .creationTime(studentSubmissionModel.getCreationTime())
                .updateTime(studentSubmissionModel.getUpdateTime())
                .course(courseMapper.toDto(studentSubmissionModel.getCourse()))
                .courseWork(courseWorkMapper.toDto(studentSubmissionModel.getCourseWork()))
                .build();
    }

    public StudentSubmissionDto toDto(StudentSubmission studentSubmission) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//        String assignedSubmission = Objects.nonNull(studentSubmission.getAssignmentSubmission()) ?
//                studentSubmission.getAssignmentSubmission().toString() :
//                "";
        LocalDateTime creationTime = null;
        LocalDateTime updateTime = null;
        try {
            creationTime = Objects.nonNull(studentSubmission.getCreationTime()) ? LocalDateTime.parse(studentSubmission.getCreationTime(), formatter) : null;
            updateTime = Objects.nonNull(studentSubmission.getUpdateTime()) ? LocalDateTime.parse(studentSubmission.getUpdateTime(), formatter) : null;
        } catch (DateTimeParseException e) {
            System.out.println(studentSubmission.getId() + ". Creation time: " + studentSubmission.getCreationTime() + ". " + e.getMessage());
        }
        return StudentSubmissionDto.builder()
                .alternateLink(studentSubmission.getAlternateLink())
                .assignedGrade(Objects.nonNull(studentSubmission.getAssignedGrade()) ? studentSubmission.getAssignedGrade() : 0)
                //.assignmentSubmission(assignedSubmission)
                .courseWorkType(studentSubmission.getCourseWorkType())
                .id(studentSubmission.getId())
                .userId(studentSubmission.getUserId())
                .state(studentSubmission.getState())
                .creationTime(creationTime)
                .updateTime(updateTime)
                .courseId(studentSubmission.getCourseId())
                .courseWorkId(studentSubmission.getCourseWorkId())
                .build();
    }
}
