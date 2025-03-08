package com.work.jobtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.jobtracker.JobTrackerApplication;
import com.work.jobtracker.dto.JobDTO;
import com.work.jobtracker.model.Job;
import com.work.jobtracker.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobController.class)
@AutoConfigureMockMvc
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @Autowired
    private ObjectMapper objectMapper;

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
        testJobDTO.setApplicationDate(LocalDateTime.now());
    }

    @Test
    void getAllJobs_ShouldReturnListOfJobs() throws Exception {
        List<Job> jobs = Arrays.asList(testJob);
        when(jobService.getAllJobs()).thenReturn(jobs);

        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].company").value("Test Company"))
                .andExpect(jsonPath("$[0].position").value("Software Engineer"));
    }

    @Test
    void getJobById_ShouldReturnJob() throws Exception {
        when(jobService.getJobById(1L)).thenReturn(testJob);

        mockMvc.perform(get("/api/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.company").value("Test Company"));
    }

    @Test
    void createJob_ShouldReturnCreatedJob() throws Exception {
        when(jobService.createJob(any(JobDTO.class))).thenReturn(testJob);

        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testJobDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.company").value("Test Company"));
    }

    @Test
    void updateJob_ShouldReturnUpdatedJob() throws Exception {
        when(jobService.updateJob(any(JobDTO.class))).thenReturn(testJob);

        mockMvc.perform(put("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testJobDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Test Company"));
    }

    @Test
    void deleteJob_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/jobs/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getJobsByStatus_ShouldReturnFilteredJobs() throws Exception {
        List<Job> jobs = Arrays.asList(testJob);
        when(jobService.getJobsByStatus("APPLIED")).thenReturn(jobs);

        mockMvc.perform(get("/api/jobs/status/APPLIED"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value("APPLIED"));
    }

    @Test
    void createJob_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        testJobDTO.setCompany(null); // Company is required

        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testJobDTO)))
                .andExpect(status().isBadRequest());
    }
} 