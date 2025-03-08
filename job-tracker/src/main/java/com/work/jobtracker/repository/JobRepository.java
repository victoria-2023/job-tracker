package com.work.jobtracker.repository;

import com.work.jobtracker.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByOrderByApplicationDateDesc();
    List<Job> findByStatus(String status);
} 