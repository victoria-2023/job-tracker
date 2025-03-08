package com.work.jobtracker.service;

import com.work.jobtracker.dto.JobDTO;
import com.work.jobtracker.model.Job;
import com.work.jobtracker.repository.JobRepository;
import com.work.jobtracker.service.impl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobServiceImpl jobService;

    private Job testJob;
    private JobDTO testJobDTO;

    @BeforeEach
    void setUp() {
        testJob = new Job();
        testJob.setId(1L);
        testJob.setCompany("Test Company");
        testJob.setPosition("Software Engineer");
        testJob.setLocation("Test Location");
        testJob.setStatus("APPLIED");
        testJob.setApplicationDate(LocalDateTime.now());
        testJob.setCreatedAt(LocalDateTime.now());
        testJob.setCreatedBy("system");
        testJob.setModifiedAt(LocalDateTime.now());
        testJob.setModifiedBy("system");

        testJobDTO = new JobDTO();
        testJobDTO.setCompany("Test Company");
        testJobDTO.setPosition("Software Engineer");
        testJobDTO.setLocation("Test Location");
        testJobDTO.setStatus("APPLIED");
    }

    @Test
    void getAllJobs_ShouldReturnListOfJobs() {
        when(jobRepository.findAll()).thenReturn(Arrays.asList(testJob));

        List<Job> result = jobService.getAllJobs();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCompany()).isEqualTo("Test Company");
        verify(jobRepository, times(1)).findAll();
    }

    @Test
    void getJobById_WhenJobExists_ShouldReturnJob() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(testJob));

        Job result = jobService.getJobById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getCompany()).isEqualTo("Test Company");
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void getJobById_WhenJobDoesNotExist_ShouldThrowException() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> jobService.getJobById(1L));
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void createJob_ShouldReturnCreatedJob() {
        when(jobRepository.save(any(Job.class))).thenReturn(testJob);

        Job result = jobService.createJob(testJobDTO);

        assertThat(result).isNotNull();
        assertThat(result.getCompany()).isEqualTo("Test Company");
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void updateJob_WhenJobExists_ShouldReturnUpdatedJob() {
        testJobDTO.setId(1L);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(testJob));
        when(jobRepository.save(any(Job.class))).thenReturn(testJob);

        Job result = jobService.updateJob(testJobDTO);

        assertThat(result).isNotNull();
        assertThat(result.getCompany()).isEqualTo("Test Company");
        verify(jobRepository, times(1)).findById(1L);
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void updateJob_WhenJobDoesNotExist_ShouldThrowException() {
        testJobDTO.setId(1L);
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> jobService.updateJob(testJobDTO));
        verify(jobRepository, times(1)).findById(1L);
        verify(jobRepository, never()).save(any(Job.class));
    }

    @Test
    void deleteJob_WhenJobExists_ShouldDeleteJob() {
        when(jobRepository.existsById(1L)).thenReturn(true);

        jobService.deleteJob(1L);

        verify(jobRepository, times(1)).existsById(1L);
        verify(jobRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteJob_WhenJobDoesNotExist_ShouldThrowException() {
        when(jobRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> jobService.deleteJob(1L));
        verify(jobRepository, times(1)).existsById(1L);
        verify(jobRepository, never()).deleteById(1L);
    }

    @Test
    void getJobsByStatus_ShouldReturnFilteredJobs() {
        when(jobRepository.findByStatus("APPLIED")).thenReturn(Arrays.asList(testJob));

        List<Job> result = jobService.getJobsByStatus("APPLIED");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo("APPLIED");
        verify(jobRepository, times(1)).findByStatus("APPLIED");
    }
} 