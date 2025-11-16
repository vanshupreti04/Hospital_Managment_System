package com.example.demo.dto;

import com.example.demo.entity.type.BloodGroupType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PatientDTO {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String email;
    private String gender;
    private BloodGroupType bloodgroup;
    private LocalDateTime createdAt;
    private Long insuranceId;
}
