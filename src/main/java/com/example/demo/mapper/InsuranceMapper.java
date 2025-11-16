package com.example.demo.mapper;

import com.example.demo.dto.InsuranceDTO;
import com.example.demo.dto.InsuranceRequestDTO;
import com.example.demo.entity.Insurance;
import com.example.demo.service.InsuranceService;

public class InsuranceMapper {
    public static InsuranceDTO toDTO(Insurance insurance){
        InsuranceDTO dto = new InsuranceDTO();
        dto.setId(insurance.getId());
        dto.setPolicyNumber(insurance.getPolicyNumber());
        dto.setProvider(insurance.getProvider());
        dto.setValidUntil(insurance.getValidUntil());
        dto.setCreatedAt(insurance.getCreatedAt());
        if(insurance.getPatient() != null){
            dto.setPatientId(insurance.getPatient().getId());
        }
        return dto;
    }
    public static Insurance toEntity(InsuranceRequestDTO dto){
        Insurance insurance = new Insurance();
        insurance.setPolicyNumber(dto.getPolicyNumber());
        insurance.setProvider(dto.getProvider());
        insurance.setValidUntil(dto.getValidUntil());
        return insurance;
    }
}
