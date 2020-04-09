package com.vasylyev.hometasks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "CourseWork")
public class CourseWorkModel {
    @Id
    private String id;
    private String alternateLink;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private CourseModel course;
    @Transient
    private String courseId;
    private LocalDateTime creationTime; //"creationTime": "2020-04-08T08:09:42.833Z",
    @Column(name = "description", length = 1000, columnDefinition = "VARCHAR(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String description;
    private LocalDateTime dueDate;
            //"dueDate": { "day": 30, "month": 4, "year": 2020    },
            //"dueTime": {"hours": 20, "minutes": 59},
    private double maxPoints;
    private String state;
    @Column(name = "title", length = 100, columnDefinition = "VARCHAR(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String title;
    private LocalDateTime updateTime; //": "2020-04-08T08:10:19.731Z",
    private String workType;
    private String topicId;
}
