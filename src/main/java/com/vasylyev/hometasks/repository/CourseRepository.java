package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel, String> {
}
