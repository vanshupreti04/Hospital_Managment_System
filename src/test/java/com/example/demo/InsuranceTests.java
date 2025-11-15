package com.example.demo;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Insurance;
import com.example.demo.entity.Patient;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.InsuranceService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
public class InsuranceTests {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void TestInsurance(){
        Insurance insurance = Insurance.builder().policyNumber("HDFC_1234").provider("HDFC").validUntil(LocalDate.of(2030,12,12)).build();
        Patient patient = insuranceService.assignInsuranceToPatient(insurance, 1L);
        System.out.println(patient);

        var newPatient = insuranceService.disassociateInsuranceFromPatient(patient.getId());
        System.out.println(newPatient);
    }

    @Test
    public void createAppointment(){
        Appointment appointment = Appointment.builder().appointmentTime(LocalDateTime.now()).reason("Cancer").build();

        var newAppointment = appointmentService.createNewAppointment(appointment, 1L,2L);
        System.out.println(newAppointment);

        var updatedAppointment = appointmentService.reAssignAppointmentToAnotherDoctor(newAppointment.getId(), 3L);
        System.out.println(updatedAppointment);
    }
}
