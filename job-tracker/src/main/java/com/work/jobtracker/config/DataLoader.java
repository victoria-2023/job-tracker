package com.work.jobtracker.config;

import com.work.jobtracker.model.Job;
import com.work.jobtracker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void run(String... args) {
        // Only load sample data if the database is empty
        if (jobRepository.count() == 0) {
            // Sample Job 1
            Job job1 = new Job();
            job1.setCompany("Tech Corp");
            job1.setPosition("Software Engineer");
            job1.setLocation("New York, NY");
            job1.setStatus("APPLIED");
            job1.setApplicationUrl("https://techcorp.com/careers");
            job1.setNotes("Applied through company website");
            job1.setApplicationDate(LocalDateTime.now().minusDays(5));
            job1.setLastUpdated(LocalDateTime.now().minusDays(5));
            jobRepository.save(job1);

            // Sample Job 2
            Job job2 = new Job();
            job2.setCompany("Startup Inc");
            job2.setPosition("Full Stack Developer");
            job2.setLocation("Remote");
            job2.setStatus("INTERVIEWING");
            job2.setApplicationUrl("https://startup.com/jobs");
            job2.setNotes("First interview scheduled for next week");
            job2.setApplicationDate(LocalDateTime.now().minusDays(10));
            job2.setLastUpdated(LocalDateTime.now().minusDays(2));
            jobRepository.save(job2);

            // Sample Job 3
            Job job3 = new Job();
            job3.setCompany("Big Tech");
            job3.setPosition("Senior Developer");
            job3.setLocation("San Francisco, CA");
            job3.setStatus("REJECTED");
            job3.setApplicationUrl("https://bigtech.com/careers");
            job3.setNotes("Received rejection email");
            job3.setApplicationDate(LocalDateTime.now().minusDays(15));
            job3.setLastUpdated(LocalDateTime.now().minusDays(1));
            jobRepository.save(job3);
        }
    }
} 