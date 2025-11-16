package com.example.demo.mapper;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.entity.Department;

import java.util.stream.Collectors;

public class DepartmentMapper {
    public static DepartmentDTO toDTO(Department department){
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setHeadDoctor(DoctorMapper.toLiteDTO(department.getHeadDoctor()));
        if(department.getDoctors() != null){
            dto.setDoctors(department.getDoctors().stream().map(DoctorMapper::toLiteDTO).collect(Collectors.toSet()));
        }
        return dto;
    }
    public static Department toEntity(DepartmentRequestDTO dto) {
        Department department = new Department();
        department.setName(dto.getName());
        return department;
    }
}
