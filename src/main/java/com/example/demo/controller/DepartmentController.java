package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentRequestDTO;
import com.example.demo.dto.DoctorDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Doctor;
import com.example.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id){
        return departmentService.getDepartmentById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<DepartmentDTO> getDepartmentByName(@PathVariable String name){
        return departmentService.getDepartmentByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public ResponseEntity<List<DepartmentDTO>> searchDepartments(@RequestParam String name){
        return ResponseEntity.ok((departmentService.searchDepartments(name)));
    }
    @GetMapping("/without-head")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsWithoutHeadDoctor(){
        return ResponseEntity.ok(departmentService.getDepartmentsWithoutHeadDoctor());
    }
    @GetMapping("/doctor-without-department")
    public ResponseEntity<List<DoctorDTO>> getDoctorWithoutDepartment(){
        return ResponseEntity.ok(departmentService.getDoctorWithoutDepartment());
    }
    @GetMapping("/stats/doctor-count")
    public ResponseEntity<Map<String,Integer>> getDoctorsCountPerDepartment(){
        return  ResponseEntity.ok(departmentService.getDoctorCountPerDepartment());
    }
    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentRequestDTO requestDTO){
        return ResponseEntity.ok(departmentService.createDepartment(requestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequestDTO requestDTO){
        return ResponseEntity.ok(departmentService.updateDepartment(id,requestDTO));
    }
    @PostMapping("/{departmentId}/set-head/{doctorId}")
    public ResponseEntity<DepartmentDTO> setHeadDoctor(@PathVariable Long departmentId,@PathVariable Long doctorId){
        return ResponseEntity.ok(departmentService.setHeadDoctor(departmentId,doctorId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

}
