package com.example.demo.service;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DoctorDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Doctor;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.mapper.DoctorMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public List<DepartmentDTO> getAllDepartments(){
        return departmentRepository.findAll().stream().map(DepartmentMapper::toDTO).collect(Collectors.toList());
    }
    public Optional<DepartmentDTO> getDepartmentById(Long id){
        return departmentRepository.findById(id).map(DepartmentMapper::toDTO);
    }
    public Optional<DepartmentDTO> getDepartmentByName(String name){
        return departmentRepository.findByName(name).map(DepartmentMapper::toDTO);
    }
    public List<DepartmentDTO> searchDepartments(String name){
        return departmentRepository.findByNameContainingIgnoreCase(name).stream().map(DepartmentMapper::toDTO).collect(Collectors.toList());
    }
    public List<DepartmentDTO> getDepartmentsWithoutHeadDoctor(){
        return departmentRepository.findDepartmentsWithoutHeadDoctor().stream().map(DepartmentMapper::toDTO).collect(Collectors.toList());
    }
    public List<DoctorDTO> getDoctorWithoutDepartment(){
        return departmentRepository.findDoctorsWithoutDepartment().stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
    public Map<String, Integer> getDoctorCountPerDepartment(){
        List<Object[]> results = departmentRepository.countDoctorsPerDepartment();
        return results.stream().collect(Collectors.toMap(obj-> (String) obj[0], obj-> ((Integer) obj[1])));
    }
    @Transactional
    public DepartmentDTO createDepartment(DepartmentRequestDTO requestDTO){
        Department department = DepartmentMapper.toEntity(requestDTO);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.toDTO(savedDepartment);
    }
    @Transactional
    public DepartmentDTO updateDepartment(Long id, DepartmentRequestDTO requestDTO){
        Department department = departmentRepository.findById(id).orElseThrow(()-> new RuntimeException("Department is not found with this id"));
        department.setName(requestDTO.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return DepartmentMapper.toDTO(updatedDepartment);
    }
    @Transactional
    public DepartmentDTO setHeadDoctor(Long departmentId, Long doctorId){
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new RuntimeException("Department is not found with this id"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor Not Found"));

        department.setHeadDoctor(doctor);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.toDTO(savedDepartment);
    }
    @Transactional
    public void deleteDepartment(Long id){
        departmentRepository.deleteById(id);
    }
}
