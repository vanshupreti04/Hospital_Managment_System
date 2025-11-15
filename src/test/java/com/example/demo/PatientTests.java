package com.example.demo;

import com.example.demo.dto.BloodGroupCountResponseEntity;
import com.example.demo.entity.Patient;
import com.example.demo.entity.type.BloodGroupType;
import com.example.demo.repository.PatientRepository;
import com.example.demo.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest

public class PatientTests {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatientRepository(){
        List<Patient> patientList = patientRepository.findAllPatientWithAppointment();
        System.out.println(patientList);
    }


    @Test
    public void testTransactionMethods(){
//        Patient patient = patientRepository.findByName("Amit");
//        List<Patient> patientList = patientRepository.findByBornAfterDate(LocalDate.of(1988,3,15));
//
        Page<Patient> patientList = patientRepository.findAllPatient(PageRequest.of(0,2, Sort.by("name")));
        for(Patient patient1 : patientList){
            System.out.println(patient1);
        }
//
//        List<Object[]> bloodGroupList = patientRepository.countEachBloodGroupType();
//        for (Object[] objects: bloodGroupList){
//            System.out.println(objects[0]+" "+objects[1]);
//        }

//        int rowsUpdated = patientRepository.updateNameWithId("Vansh",1);

//        List<BloodGroupCountResponseEntity> bloodGroupList = patientRepository.countEachBloodGroupType();
//        for (BloodGroupCountResponseEntity bloodGroupCountResponse: bloodGroupList){
//            System.out.println(bloodGroupCountResponse);
//        }
    }
}
