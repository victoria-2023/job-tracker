package com.work.jobtracker.repository;

import com.work.jobtracker.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class JobRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JobRepository jobRepository;

    private Job testJob;

    @BeforeEach
    void setUp() {
        testJob = new Job();
        testJob.setCompany("Test Company");
        testJob.setPosition("Software Engineer");
        testJob.setLocation("Test Location");
        testJob.setStatus("APPLIED");
        testJob.setApplicationDate(LocalDateTime.now());
        testJob.setCreatedAt(LocalDateTime.now());
        testJob.setCreatedBy("system");
        testJob.setModifiedAt(LocalDateTime.now());
        testJob.setModifiedBy("system");
    }

    @Test
    void findByStatus_ShouldReturnJobsWithMatchingStatus() {
        entityManager.persist(testJob);

        Job anotherJob = new Job();
        anotherJob.setCompany("Another Company");
        anotherJob.setPosition("Developer");
        anotherJob.setLocation("Another Location");
        anotherJob.setStatus("INTERVIEWING");
        anotherJob.setApplicationDate(LocalDateTime.now());
        anotherJob.setCreatedAt(LocalDateTime.now());
        anotherJob.setCreatedBy("system");
        anotherJob.setModifiedAt(LocalDateTime.now());
        anotherJob.setModifiedBy("system");
        entityManager.persist(anotherJob);

        entityManager.flush();

        List<Job> foundJobs = jobRepository.findByStatus("APPLIED");

        assertThat(foundJobs).hasSize(1);
        assertThat(foundJobs.get(0).getCompany()).isEqualTo("Test Company");
        assertThat(foundJobs.get(0).getStatus()).isEqualTo("APPLIED");
    }

    @Test
    void save_ShouldPersistJob() {
        Job savedJob = jobRepository.save(testJob);

        Job foundJob = entityManager.find(Job.class, savedJob.getId());

        assertThat(foundJob).isNotNull();
        assertThat(foundJob.getCompany()).isEqualTo("Test Company");
        assertThat(foundJob.getPosition()).isEqualTo("Software Engineer");
    }

    @Test
    void findById_WhenJobExists_ShouldReturnJob() {
        Job persistedJob = entityManager.persist(testJob);
        entityManager.flush();

        Job foundJob = jobRepository.findById(persistedJob.getId()).orElse(null);

        assertThat(foundJob).isNotNull();
        assertThat(foundJob.getCompany()).isEqualTo("Test Company");
    }

    @Test
    void findById_WhenJobDoesNotExist_ShouldReturnEmpty() {
        Job foundJob = jobRepository.findById(999L).orElse(null);

        assertThat(foundJob).isNull();
    }

    @Test
    void deleteById_ShouldRemoveJob() {
        Job persistedJob = entityManager.persist(testJob);
        entityManager.flush();

        jobRepository.deleteById(persistedJob.getId());

        Job foundJob = entityManager.find(Job.class, persistedJob.getId());
        assertThat(foundJob).isNull();
    }

    @Test
    void findAll_ShouldReturnAllJobs() {
        entityManager.persist(testJob);
        
        Job anotherJob = new Job();
        anotherJob.setCompany("Another Company");
        anotherJob.setPosition("Developer");
        anotherJob.setLocation("Another Location");
        anotherJob.setStatus("INTERVIEWING");
        anotherJob.setApplicationDate(LocalDateTime.now());
        anotherJob.setCreatedAt(LocalDateTime.now());
        anotherJob.setCreatedBy("system");
        anotherJob.setModifiedAt(LocalDateTime.now());
        anotherJob.setModifiedBy("system");
        entityManager.persist(anotherJob);

        entityManager.flush();

        List<Job> allJobs = jobRepository.findAll();

        assertThat(allJobs).hasSize(2);
    }
} 