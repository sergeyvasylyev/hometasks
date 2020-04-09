package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.model.CourseWorkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseWorkRepository extends JpaRepository<CourseWorkModel, String> {
}
