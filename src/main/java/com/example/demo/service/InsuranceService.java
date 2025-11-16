package com.example.demo.service;

import com.example.demo.dto.InsuranceDTO;
import com.example.demo.dto.InsuranceRequestDTO;
import com.example.demo.dto.PatientDTO;
import com.example.demo.entity.Insurance;
import com.example.demo.entity.Patient;
import com.example.demo.mapper.InsuranceMapper;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.repository.InsuranceRepository;
import com.example.demo.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    public List<InsuranceDTO> getAllInsurance(){
        return insuranceRepository.findAll().stream().map(InsuranceMapper::toDTO).collect(Collectors.toList());
    }
    public List<InsuranceDTO> getAllInsuranceWithPatients(){
        return insuranceRepository.findAllWithPatient().stream().map(InsuranceMapper::toDTO).collect(Collectors.toList());
    }
    public Optional<InsuranceDTO> getInsuranceById(Long id){
        return insuranceRepository.findById(id).map(InsuranceMapper::toDTO);
    }
    public Optional<InsuranceDTO> getInsuranceByPolicyNumber(String policyNumber){
        return insuranceRepository.findByPolicyNumber(policyNumber).map(InsuranceMapper::toDTO);
    }
    public List<InsuranceDTO> getInsuranceByProvider(String provider){
        return insuranceRepository.findByProviderContainingIgnoreCase(provider).stream().map(InsuranceMapper::toDTO).collect(Collectors.toList());
    }
    public List<InsuranceDTO> getExpiringPolicies(LocalDate startDate, LocalDate endDate){
        return insuranceRepository.findExpiringPolicies(startDate, endDate).stream().map(InsuranceMapper::toDTO).collect(Collectors.toList());
    }
    public List<InsuranceDTO> getExpiredPolicies(){
        return insuranceRepository.findExpiredPolicies(LocalDate.now()).stream().map(InsuranceMapper::toDTO).collect(Collectors.toList());
    }
    public Map<String,Long> getPolicyCountByProvider(){
        List<Object[]> results = insuranceRepository.countPoliciesByProvider();
        return results.stream().collect(Collectors.toMap(obj->(String)obj[0],obj->(Long) obj[1]));
    }

    @Transactional
    public InsuranceDTO createInsurance(InsuranceRequestDTO requestDTO){
        Insurance insurance = InsuranceMapper.toEntity(requestDTO);
        Insurance savedInsurance = insuranceRepository.save(insurance);
        return InsuranceMapper.toDTO(savedInsurance);
    }
    @Transactional
    public InsuranceDTO updateInsurance(Long id, InsuranceRequestDTO requestDTO){
        Insurance insurance = insuranceRepository.findById(id).orElseThrow();
        insurance.setPolicyNumber(requestDTO.getPolicyNumber());
        insurance.setProvider(requestDTO.getProvider());
        insurance.setValidUntil(requestDTO.getValidUntil());

        Insurance savedInsurance = insuranceRepository.save(insurance);
        return InsuranceMapper.toDTO(savedInsurance);
    }
    @Transactional
    public PatientDTO assignInsuranceToPatient(InsuranceRequestDTO requestDTO, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new EntityNotFoundException("patient not found with id:" +patientId));

        Insurance insurance = InsuranceMapper.toEntity(requestDTO);
        insurance.setPatient(patient);
        patient.setInsurance(insurance);

        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(savedPatient);
    }

    @Transactional
    public PatientDTO disassociateInsuranceFromPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new EntityNotFoundException("patient not found with id:" +patientId));
        patient.setInsurance(null);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(savedPatient);
    }
    @Transactional
    public void deleteInsurance(Long id){
        Insurance insurance = insuranceRepository.findById(id).orElseThrow();
        insuranceRepository.deleteById(insurance.getId());
    }
}
