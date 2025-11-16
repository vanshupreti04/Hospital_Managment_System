package com.example.demo.dto;

import com.example.demo.entity.type.BloodGroupType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDTO {
    String name;
    private LocalDate birthdate;
    private String email;
    private String gender;
    private BloodGroupType blooddgroup;
}
