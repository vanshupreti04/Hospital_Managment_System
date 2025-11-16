package com.example.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private String specilization;
    private String email;
    private Set<Long> departmentId;
}
