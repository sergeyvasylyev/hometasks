package com.vasylyev.hometasks.service;

import com.google.api.services.classroom.model.StudentSubmission;
import com.vasylyev.hometasks.dto.StudentSubmissionDto;

import java.util.List;

public interface StudentSubmissionService {

    StudentSubmissionDto save(StudentSubmissionDto studentSubmissionDto);

    StudentSubmissionDto findById(String id);

    List<StudentSubmissionDto> findByCourseWorkId(String courseworkId);

    List<StudentSubmissionDto> fillCourseAndCourseWorkById(List<StudentSubmissionDto> studentSubmissionDtoList);

    List<String> findAllCourseWorkIdWithGrades();

    void addStudentSubmission(List<StudentSubmissionDto> studentSubmissionDtoList);

}
