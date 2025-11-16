package com.example.demo.mapper;

import com.example.demo.dto.PatientDTO;
import com.example.demo.dto.PatientLiteDTO;
import com.example.demo.dto.PatientRequestDTO;
import com.example.demo.entity.Patient;

public class PatientMapper {
    public static PatientDTO toDTO(Patient patient){
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setBirthdate(patient.getBirthdate());
        dto.setEmail(patient.getEmail());
        dto.setGender(patient.getGender());
        dto.setBloodgroup(patient.getBloodgroup());
        dto.setCreatedAt(patient.getCreatedAt());
        if(patient.getInsurance() != null){
            dto.setInsuranceId(patient.getInsurance().getId());
        }
        return dto;
    }
    public static PatientLiteDTO toLiteDTO(Patient patient){
        PatientLiteDTO dto = new PatientLiteDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        return dto;
    }
    public static Patient toEntity(PatientRequestDTO dto){
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setBirthdate(dto.getBirthdate());
        patient.setEmail(dto.getEmail());
        patient.setGender(dto.getGender());
        patient.setBloodgroup(dto.getBlooddgroup());
        return patient;
    }
}
