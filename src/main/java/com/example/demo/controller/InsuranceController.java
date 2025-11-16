package com.example.demo.controller;

import com.example.demo.dto.InsuranceDTO;
import com.example.demo.dto.InsuranceRequestDTO;
import com.example.demo.dto.PatientDTO;
import com.example.demo.entity.Insurance;
import com.example.demo.entity.Patient;
import com.example.demo.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping
    public ResponseEntity<List<InsuranceDTO>> getAllInsurance(){
        return ResponseEntity.ok(insuranceService.getAllInsurance());
    }
    @GetMapping("/with-patients")
    public ResponseEntity<List<InsuranceDTO>> getAllInsuranceWithPatients(){
        return ResponseEntity.ok(insuranceService.getAllInsuranceWithPatients());
    }
    @GetMapping("/{id}")
    public ResponseEntity<InsuranceDTO> getInsuranceById(@PathVariable Long id){
        return insuranceService.getInsuranceById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/policy/{policyNumber}")
    public ResponseEntity<InsuranceDTO> getInsuranceByPolicyNumber(@PathVariable String policyNumber){
        return insuranceService.getInsuranceByPolicyNumber(policyNumber).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<InsuranceDTO>> getInsuranceByProvider(@PathVariable String provider){
        return ResponseEntity.ok(insuranceService.getInsuranceByProvider(provider));
    }
    @GetMapping("/expiring")
    public ResponseEntity<List<InsuranceDTO>> getExpiringPolicies(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate){
        return ResponseEntity.ok(insuranceService.getExpiringPolicies(startDate, endDate));
    }
    @GetMapping("/expired")
    public ResponseEntity<List<InsuranceDTO>> getExpiredPolicies(){
        return ResponseEntity.ok(insuranceService.getExpiredPolicies());
    }
    @GetMapping("/stats/provider-count")
    public ResponseEntity<Map<String,Long>> getPolicyCountByProvider(){
        return ResponseEntity.ok(insuranceService.getPolicyCountByProvider());
    }
    @PostMapping
    public ResponseEntity<InsuranceDTO> createInsurance(@RequestBody InsuranceRequestDTO requestDTO){
        return ResponseEntity.ok(insuranceService.createInsurance(requestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<InsuranceDTO> updateInsurance(@PathVariable Long id, @RequestBody InsuranceRequestDTO requestDTO){
        return ResponseEntity.ok(insuranceService.updateInsurance(id, requestDTO));
    }
    @PostMapping("/assign-to-patient/{patientId}")
    public ResponseEntity<PatientDTO> assignInsuranceToPatient(@RequestBody InsuranceRequestDTO requestDTO, @PathVariable Long patientId){
        return ResponseEntity.ok(insuranceService.assignInsuranceToPatient(requestDTO, patientId));
    }
    @PostMapping("/disassociate-from-patient/{patientId}")
    public ResponseEntity<PatientDTO> disassociateInsuranceFromPatient(@PathVariable Long patientId){
        return ResponseEntity.ok(insuranceService.disassociateInsuranceFromPatient(patientId));
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteInsurance(@PathVariable Long id){
        insuranceService.deleteInsurance(id);
        return ResponseEntity.ok().build();
    }
}
