package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.repository.JobHistoryRepository;
import com.vasylyev.hometasks.scheduler.model.JobHistory;
import com.vasylyev.hometasks.service.JobHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;

    @Override
    public JobHistory saveJobHistory(JobHistory jobHistory) {
        return jobHistoryRepository.save(jobHistory);
    }

    @Override
    public List<JobHistory> getAllJobHistory() {
        return jobHistoryRepository.findAll();
    }
}
