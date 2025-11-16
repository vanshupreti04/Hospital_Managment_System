package com.example.demo.controller;

import com.example.demo.dto.BloodGroupCountResponseEntity;
import com.example.demo.dto.PatientDTO;
import com.example.demo.entity.Patient;
import com.example.demo.entity.type.BloodGroupType;
import com.example.demo.repository.PatientRepository;
import com.example.demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor

/* API Testing :


*/

public class PatientController {

    private final PatientService patientService;
    private final PatientRepository patientRepository;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<PatientDTO> getPatientByName(@PathVariable String name){
        return patientService.getPatientByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> searchPatients(@RequestParam String query){
        return ResponseEntity.ok(patientService.searchPatients(query));
    }
    @GetMapping("/blood-group/{bloodGroup}")
    public ResponseEntity<List<PatientDTO>> getPatientByBloodGroup(@PathVariable BloodGroupType bloodGroup){
        return ResponseEntity.ok(patientService.getPatientByBloodGroup(bloodGroup));
    }
    @GetMapping("/born-after")
    public ResponseEntity<List<PatientDTO>> getPatientsBornAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dateTime){
        return ResponseEntity.ok(patientService.getPatientsBornAfter(dateTime));
    }
    @GetMapping("/stats/blood-group")
    public ResponseEntity<List<BloodGroupCountResponseEntity>> getBloodGroupStats(){
        return ResponseEntity.ok(patientService.getBloodGroupStats());
    }
    @GetMapping("with-appointments")
    public ResponseEntity<List<PatientDTO>> getPatientWithAppointments(){
        return ResponseEntity.ok(patientService.getPatientWithAppointments());
    }
    @PutMapping("/{id}/name")
    public ResponseEntity<Integer> updatePatientName(@PathVariable Long id, @RequestParam String name){
        int updated = patientRepository.updateNameWithId(name, id.intValue());
        return ResponseEntity.ok(updated);
    }
}
