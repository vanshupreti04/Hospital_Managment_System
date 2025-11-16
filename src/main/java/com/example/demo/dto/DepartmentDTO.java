package com.example.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private DoctorLiteDTO headDoctor;
    private Set<DoctorLiteDTO> doctors;
}

