package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.model.CourseWorkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseWorkRepository extends JpaRepository<CourseWorkModel, String> {

    @Query("SELECT cw FROM CourseWorkModel cw WHERE cw.course.id = ?1")
    List<CourseWorkModel> findByCourseId(String courseId);
}
