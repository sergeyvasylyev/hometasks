package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.scheduler.model.JobHistory;

import java.util.List;

public interface JobHistoryService {

    JobHistory saveJobHistory(JobHistory jobHistory);

    List<JobHistory> getAllJobHistory();

}
