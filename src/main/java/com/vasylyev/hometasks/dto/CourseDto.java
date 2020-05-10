package com.vasylyev.hometasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CourseDto {
    private String id;
    private String alternateLink;
    private String courseState;
    private String descriptionHeading;
    private String enrollmentCode;
    private String name;
    private String room;
    private String section;
    private AccountDto account;
}
