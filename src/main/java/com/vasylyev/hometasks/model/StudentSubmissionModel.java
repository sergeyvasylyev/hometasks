package com.vasylyev.hometasks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "student_submission")
public class StudentSubmissionModel {

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private CourseModel course;
    @Transient
    private String courseId;
    @ManyToOne
    @JoinColumn(name = "courseWorkId")
    private CourseWorkModel courseWork;
    @Transient
    private String courseWorkId;

    private String alternateLink;
    private double assignedGrade;
    private String assignmentSubmission;
    private String courseWorkType;
    private LocalDateTime creationTime;
    private String state;
    private LocalDateTime updateTime;
    private String userId;

}
