package com.example.demo.service;

import com.example.demo.entity.Insurance;
import com.example.demo.entity.Patient;
import com.example.demo.repository.InsuranceRepository;
import com.example.demo.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new EntityNotFoundException("patient not found with id:" +patientId));

        patient.setInsurance(insurance);
        insurance.setPatient(patient);

        return patient;
    }

    @Transactional
    public Patient disassociateInsuranceFromPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new EntityNotFoundException("patient not found with id:" +patientId));
        patient.setInsurance(null);
        return patient;
    }
}
