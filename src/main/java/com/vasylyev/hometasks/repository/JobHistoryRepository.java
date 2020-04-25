package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.scheduler.model.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {
}
