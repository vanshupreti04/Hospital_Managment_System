package com.example.demo.mapper;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.dto.DoctorLiteDTO;
import com.example.demo.dto.DoctorRequestDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Doctor;

import java.util.stream.Collectors;

public class DoctorMapper {
    public static DoctorDTO toDTO(Doctor doctor){
        DoctorDTO dto= new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecilization(doctor.getSpecilization());
        dto.setEmail(doctor.getEmail());
        if(doctor.getDepartments() != null){
            dto.setDepartmentId(doctor.getDepartments().stream().map(Department::getId).collect(Collectors.toSet()));
        }
        return dto;
    }
    public static DoctorLiteDTO toLiteDTO(Doctor doctor) {
        DoctorLiteDTO dto = new DoctorLiteDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecilization(doctor.getSpecilization());
        return dto;
    }
    public static Doctor toEntity(DoctorRequestDTO dto) {
        if (dto == null) return null;
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecilization(dto.getSpecilization());
        doctor.setEmail(dto.getEmail());
        return doctor;
    }
    public static void toEntity(DoctorRequestDTO dto, Doctor doctor) {
        if (dto == null || doctor == null) return;
        doctor.setName(dto.getName());
        doctor.setSpecilization(dto.getSpecilization());
        doctor.setEmail(dto.getEmail());
    }
}
