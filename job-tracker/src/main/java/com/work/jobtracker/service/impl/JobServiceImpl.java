package com.work.jobtracker.service.impl;

import com.work.jobtracker.dto.JobDTO;
import com.work.jobtracker.model.Job;
import com.work.jobtracker.repository.JobRepository;
import com.work.jobtracker.service.JobService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
    }

    @Override
    public Job createJob(JobDTO jobDTO) {
        Job job = new Job();
        updateJobFromDTO(job, jobDTO);
        job.setCreatedAt(LocalDateTime.now());
        job.setCreatedBy("system");
        job.setModifiedAt(LocalDateTime.now());
        job.setModifiedBy("system");
        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(JobDTO jobDTO) {
        Job existingJob = getJobById(jobDTO.getId());
        updateJobFromDTO(existingJob, jobDTO);
        existingJob.setModifiedAt(LocalDateTime.now());
        existingJob.setModifiedBy("system");
        return jobRepository.save(existingJob);
    }

    @Override
    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }

    @Override
    public List<Job> getJobsByStatus(String status) {
        return jobRepository.findByStatus(status);
    }

    private void updateJobFromDTO(Job job, JobDTO jobDTO) {
        job.setCompany(jobDTO.getCompany());
        job.setPosition(jobDTO.getPosition());
        job.setLocation(jobDTO.getLocation());
        job.setStatus(jobDTO.getStatus());
        job.setApplicationDate(jobDTO.getApplicationDate() != null ? 
            jobDTO.getApplicationDate() : LocalDateTime.now());
    }
} 