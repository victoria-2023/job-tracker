package com.work.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JobDTO {
    private Long id;

    @NotBlank(message = "Company name is required")
    private String company;

    @NotBlank(message = "Position is required")
    private String position;

    @NotBlank(message = "Location is required")
    private String location;

    private String applicationUrl;

    @NotBlank(message = "Status is required")
    private String status;

    private String notes;
    
    private LocalDateTime applicationDate;
} 