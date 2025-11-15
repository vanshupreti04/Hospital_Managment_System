package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.entity.Doctor;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }
    public Page<Doctor> getAllDoctors(Pageable pageable){
        return doctorRepository.findAll(pageable);
    }
    public Optional<Doctor> getDoctorById(Long id){
        return doctorRepository.findById(id);
    }
    public Optional<Doctor> getDoctorByEmail(String email){
        return doctorRepository.findByEmail(email);
    }
    public List<Doctor> getDoctorBySpecilization(String specilization){
        return doctorRepository.findBySpecilizationContainingIgnoreCase(specilization);
    }
    public List<Doctor> searchDoctorByName(String name){
        return doctorRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Doctor> getAvailableDoctors(){
        return doctorRepository.findAvailableDoctors();
    }
    public List<Doctor> getDoctorByDepartment(Long departmentId){
        return doctorRepository.findByDepartmentId(departmentId);
    }
    public List<Doctor> getHeadDoctors(){
        return doctorRepository.findHeadDoctors();
    }
    public Map<String,Long> getDoctorsCountBySpecialization(){
        List<Object[]> result = doctorRepository.countDoctorsBySpecilization();
        return result.stream().collect(Collectors.toMap(obj -> (String) obj[0], obj-> (Long) obj[1]));
    }
    @Transactional
    public Doctor createDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }
    @Transactional
    public Doctor updateDoctor(Long id, Doctor doctorDetails){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()-> new RuntimeException("Doctor Not found with id :" +id));

        doctor.setName(doctorDetails.getName());
        doctor.setSpecilization(doctorDetails.getSpecilization());
        doctor.setEmail(doctorDetails.getEmail());

        return doctorRepository.save(doctor);
    }
    @Transactional
    public void deleteDoctor(Long id){
        doctorRepository.deleteById(id);
    }
    @Transactional
    public Doctor assignToDepartment(Long doctorId, Long departmentId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new RuntimeException("Doctor not Found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new RuntimeException("Department Not Found"));

        doctor.getDepartments().add(department);
        return doctorRepository.save(doctor);
    }
}
