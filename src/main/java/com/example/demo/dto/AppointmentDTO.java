package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private PatientLiteDTO patient;
    private DoctorLiteDTO doctor;
}
