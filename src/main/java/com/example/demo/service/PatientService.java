package com.example.demo.service;

import com.example.demo.dto.BloodGroupCountResponseEntity;
import com.example.demo.dto.PatientDTO;
import com.example.demo.entity.Patient;
import com.example.demo.entity.type.BloodGroupType;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PatientDTO> getPatientByName(String name) {
        return Optional.ofNullable(patientRepository.findByName(name))
                .map(PatientMapper::toDTO);
    }

    public List<PatientDTO> searchPatients(String query) {
        return patientRepository.findByNameContaining(query).stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PatientDTO> getPatientByBloodGroup(BloodGroupType bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup).stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PatientDTO> getPatientsBornAfter(LocalDateTime dateTime) {
        return patientRepository.findByBornAfterDate(dateTime).stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<BloodGroupCountResponseEntity> getBloodGroupStats() {
        return patientRepository.countEachBloodGroupType();
    }

    public List<PatientDTO> getPatientWithAppointments() {
        // This query fetches related entities, but the mapper flattens it
        return patientRepository.findAllPatientWithAppointment().stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public int updatePatientName(Long id, String name) {
        return patientRepository.updateNameWithId(name, id.intValue());
    }
}
