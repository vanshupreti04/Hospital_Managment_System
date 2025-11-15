package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.entity.Doctor;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.plaf.OptionPaneUI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }
    public Optional<Department> getDepartmentById(Long id){
        return departmentRepository.findById(id);
    }
    public Optional<Department> getDepartmentByName(String name){
        return departmentRepository.findByName(name);
    }
    public List<Department> searchDepartments(String name){
        return departmentRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Department> getDepartmentsWithoutHeadDoctor(){
        return departmentRepository.findDepartmentsWithoutHeadDoctor();
    }
    public List<Doctor> getDoctorWithoutDepartment(){
        return departmentRepository.findDoctorsWithoutDepartment();
    }
    public Map<String,Integer> getDoctorCountPerDepartment(){
        List<Object[]> results = departmentRepository.countDoctorsPerDepartment();
        return results.stream().collect(Collectors.toMap(obj-> (String) obj[0], obj-> ((Long) obj[1]).intValue()));
    }
    @Transactional
    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }
    @Transactional
    public Department updateDepartment(Long id, Department departmentDetails){
        Department department = departmentRepository.findById(id).orElseThrow(()-> new RuntimeException("Department is not found with this id"));
        department.setName(departmentDetails.getName());
        department.setHeadDoctor(departmentDetails.getHeadDoctor());

        return departmentRepository.save((department));
    }
    @Transactional
    public Department setHeadDoctor(Long departmentId, Long doctorId){
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new RuntimeException("Department is not found with this id"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor Not Found"));

        department.setHeadDoctor(doctor);
        return departmentRepository.save(department);
    }
    @Transactional
    public void deleteDepartment(Long id){
        departmentRepository.deleteById(id);
    }
}
