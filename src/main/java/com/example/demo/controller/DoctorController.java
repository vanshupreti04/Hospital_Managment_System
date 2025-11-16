package com.example.demo.controller;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.dto.DoctorRequestDTO;
import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
    @GetMapping("/page")
    public ResponseEntity<Page<DoctorDTO>> getAllDoctors(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size, @RequestParam(defaultValue = "name")String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id){
        return doctorService.getDoctorById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<DoctorDTO> getDoctorByEmail(@PathVariable String email){
        return doctorService.getDoctorByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("specilization/{specilization}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecilization(@PathVariable String specilization){
        return ResponseEntity.ok(doctorService.getDoctorBySpecilization(specilization));
    }
    @GetMapping("/search")
    public ResponseEntity<List<DoctorDTO>> searchDoctors(@RequestParam String name){
        return ResponseEntity.ok(doctorService.searchDoctorByName(name));
    }
    @GetMapping("/available")
    public ResponseEntity<List<DoctorDTO>> getAvaialbleDoctors(){
        return ResponseEntity.ok(doctorService.getAvailableDoctors());
    }
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByDepartment(@PathVariable Long departmentId){
        return ResponseEntity.ok(doctorService.getDoctorByDepartment(departmentId));
    }
    @GetMapping("head-docctors")
    public ResponseEntity<List<DoctorDTO>> getHeadDoctors(){
        return ResponseEntity.ok(doctorService.getHeadDoctors());
    }
    @GetMapping("/stats/specilization")
    public ResponseEntity<Map<String,Long>> getDoctorsCountBySpecilization(){
        return ResponseEntity.ok(doctorService.getDoctorsCountBySpecialization());
    }
    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorRequestDTO requestDTO){
        return ResponseEntity.ok(doctorService.createDoctor(requestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorRequestDTO requestDTO){
        return ResponseEntity.ok(doctorService.updateDoctor(id,requestDTO));
    }
    @PostMapping("/{doctorId}/assign-doctor/{departmentId}")
    public ResponseEntity<DoctorDTO> assignToDepartment(@PathVariable Long doctorId, @PathVariable Long departmentId){
        return ResponseEntity.ok(doctorService.assignToDepartment(doctorId,departmentId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }
}
