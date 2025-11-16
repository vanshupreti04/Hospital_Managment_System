package com.example.demo.service;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.dto.DoctorRequestDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Doctor;
import com.example.demo.mapper.DoctorMapper;
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

    public List<DoctorDTO> getAllDoctors(){
        return doctorRepository.findAll().stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
    public Page<DoctorDTO> getAllDoctors(Pageable pageable){
        return doctorRepository.findAll(pageable).map(DoctorMapper::toDTO);
    }
    public Optional<DoctorDTO> getDoctorById(Long id){
        return doctorRepository.findById(id).map(DoctorMapper::toDTO);
    }
    public Optional<DoctorDTO> getDoctorByEmail(String email){
        return doctorRepository.findByEmail(email).map(DoctorMapper::toDTO);
    }
    public List<DoctorDTO> getDoctorBySpecilization(String specilization){
        return doctorRepository.findBySpecilizationContainingIgnoreCase(specilization).stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
    public List<DoctorDTO> searchDoctorByName(String name){
        return doctorRepository.findByNameContainingIgnoreCase(name).stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
    public List<DoctorDTO> getAvailableDoctors(){
        return doctorRepository.findAvailableDoctors().stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
    public List<DoctorDTO> getDoctorByDepartment(Long departmentId){
        return doctorRepository.findByDepartmentId(departmentId).stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
    public List<DoctorDTO> getHeadDoctors(){
        return doctorRepository.findHeadDoctors().stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
    public Map<String,Long> getDoctorsCountBySpecialization(){
        List<Object[]> result = doctorRepository.countDoctorsBySpecilization();
        return result.stream().collect(Collectors.toMap(obj -> (String) obj[0], obj-> (Long) obj[1]));
    }
    @Transactional
    public DoctorDTO createDoctor(DoctorRequestDTO requestDTO){
        Doctor doctor = DoctorMapper.toEntity(requestDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.toDTO(savedDoctor);
    }
    @Transactional
    public DoctorDTO updateDoctor(Long id, DoctorRequestDTO requestDTO){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()-> new RuntimeException("Doctor Not found with id :" +id));

        DoctorMapper.toEntity(requestDTO,doctor);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.toDTO(savedDoctor);
    }
    @Transactional
    public void deleteDoctor(Long id){
        doctorRepository.deleteById(id);
    }
    @Transactional
    public DoctorDTO assignToDepartment(Long doctorId, Long departmentId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new RuntimeException("Doctor not Found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new RuntimeException("Department Not Found"));

        doctor.getDepartments().add(department);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.toDTO(savedDoctor);
    }
}
