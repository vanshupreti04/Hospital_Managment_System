package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InsuranceRequestDTO {
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;
}
