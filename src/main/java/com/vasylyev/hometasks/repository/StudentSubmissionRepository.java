package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.model.StudentSubmissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentSubmissionRepository extends JpaRepository<StudentSubmissionModel, String> {

    @Query("SELECT s FROM StudentSubmissionModel s WHERE s.courseWork.id = ?1")
    List<StudentSubmissionModel> findByCourseWorkId(String courseworkId);

    @Query("SELECT s.courseWork.id FROM StudentSubmissionModel s WHERE s.assignedGrade > 0")
    List<String> findAllCourseWorkWithGrade();

}
