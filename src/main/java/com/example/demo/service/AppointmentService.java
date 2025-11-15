package com.example.demo.service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }
    public Page<Appointment> getAllAppointments(Pageable pageable){
        return appointmentRepository.findAll(pageable);
    }
    public Optional<Appointment> getAppointmentById(Long id){
        return appointmentRepository.findById(id);
    }
    public Optional<Appointment> getAppointmentWithDetails(Long id){
        return appointmentRepository.findByIdWithDetails(id);
    }
    public List<Appointment> getAppointmentsByPatient(Long patientId){
        return appointmentRepository.findByPatientId(patientId);
    }
    public List<Appointment> getAppointmentByDoctor(Long doctorId){
        return appointmentRepository.findByDoctorId(doctorId);
    }
    public List<Appointment> getUpcomingAppointments(){
        return appointmentRepository.findUpcomingAppointments(LocalDateTime.now());
    }
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end){
        return appointmentRepository.findAppointmentTimeBetween(start,end);
    }
    public List<Appointment> getDoctorAppointmentByDateRange(Long doctorId, LocalDateTime start, LocalDateTime end){
        return appointmentRepository.findByDoctorAndDateRange(doctorId,start,end);
    }
    public Map<String,Long> getAppointmentCountByDoctor(){
        List<Object[]> results = appointmentRepository.countAppointmentByDoctor();
        return results.stream().collect(Collectors.toMap(obj-> ((Doctor) obj[0]).getName(), obj-> (Long) obj[1]));
    }

    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long doctorId, Long patientId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId() != null){
            throw new IllegalArgumentException("Appointment is not correct");
        }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment);
        return appointmentRepository.save(appointment);
    }
    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);

        doctor.getAppointments().add(appointment);
        return appointment;
    }
    @Transactional
    public Appointment updateAppointment(Long id, Appointment appointmentDetails){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        appointment.setReason(appointmentDetails.getReason());

        return appointmentRepository.save(appointment);
    }
    @Transactional
    public void deleteAppointment(Long id){
        appointmentRepository.deleteById(id);
    }
    public boolean isAppointmentTimeAvailable(Long doctorId, LocalDateTime appointmentTime){
        List<Appointment> overlapping = appointmentRepository.findOverlappingAppointments(doctorId,appointmentTime);
        return overlapping.isEmpty();
    }
}
