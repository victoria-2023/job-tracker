package com.work.jobtracker.service;

import com.work.jobtracker.dto.JobDTO;
import com.work.jobtracker.model.Job;
import java.util.List;

public interface JobService {
    List<Job> getAllJobs();
    Job getJobById(Long id);
    Job createJob(JobDTO jobDTO);
    Job updateJob(JobDTO jobDTO);
    void deleteJob(Long id);
    List<Job> getJobsByStatus(String status);
} 