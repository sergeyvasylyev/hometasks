package com.vasylyev.hometasks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Course")
public class CourseModel {
    @Id
    private String id;
    private String alternateLink;

    @Column(name = "sourceOfFund", length = 100, columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String sourceOfFunds;
    private String courseState;
    @Column(name = "descriptionHeading", length = 500, columnDefinition = "VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String descriptionHeading;
    private String enrollmentCode;
    @Column(name = "name", length = 100, columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String name;
    @Column(name = "room", length = 100, columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String room;
    @Column(name = "section", length = 100, columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String section;
}
