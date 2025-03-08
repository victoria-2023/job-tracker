package com.work.jobtracker.controller;

import com.work.jobtracker.dto.JobDTO;
import com.work.jobtracker.model.Job;
import com.work.jobtracker.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody JobDTO jobDTO) {
        Job createdJob = jobService.createJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @PutMapping
    public ResponseEntity<Job> updateJob(@Valid @RequestBody JobDTO jobDTO) {
        return ResponseEntity.ok(jobService.updateJob(jobDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Job>> getJobsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(jobService.getJobsByStatus(status));
    }
} 