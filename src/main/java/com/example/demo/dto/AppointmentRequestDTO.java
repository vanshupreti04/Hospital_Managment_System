package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {
    private LocalDateTime appointmentTime;
    private String reason;
}
