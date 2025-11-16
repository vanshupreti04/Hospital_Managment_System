package com.example.demo.service;

import com.example.demo.dto.AppointmentDTO;
import com.example.demo.dto.AppointmentRequestDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.mapper.AppointmentMapper;
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

    public List<AppointmentDTO> getAllAppointments(){
        return appointmentRepository.findAll().stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }
    public Page<AppointmentDTO> getAllAppointments(Pageable pageable){
        return appointmentRepository.findAll(pageable).map(AppointmentMapper::toDTO);
    }
    public Optional<AppointmentDTO> getAppointmentById(Long id){
        return appointmentRepository.findById(id).map(AppointmentMapper::toDTO);
    }
    public Optional<AppointmentDTO> getAppointmentWithDetails(Long id){
        return appointmentRepository.findByIdWithDetails(id).map(AppointmentMapper::toDTO);
    }
    public List<AppointmentDTO> getAppointmentsByPatient(Long patientId){
        return appointmentRepository.findByPatientId(patientId).stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }
    public List<AppointmentDTO> getAppointmentByDoctor(Long doctorId){
        return appointmentRepository.findByDoctorId(doctorId).stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }
    public List<AppointmentDTO> getUpcomingAppointments(){
        return appointmentRepository.findUpcomingAppointments(LocalDateTime.now()).stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }
    public List<AppointmentDTO> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end){
        return appointmentRepository.findByAppointmentTimeBetween(start,end).stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }
    public List<AppointmentDTO> getDoctorAppointmentByDateRange(Long doctorId, LocalDateTime start, LocalDateTime end){
        return appointmentRepository.findByDoctorAndDateRange(doctorId,start,end).stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }
    public Map<String,Long> getAppointmentCountByDoctor(){
        List<Object[]> results = appointmentRepository.countAppointmentByDoctor();
        return results.stream().collect(Collectors.toMap(obj-> ((Doctor) obj[0]).getName(), obj-> (Long) obj[1]));
    }

    @Transactional
    public AppointmentDTO createNewAppointment(AppointmentRequestDTO requestDTO, Long doctorId, Long patientId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        Appointment appointment = AppointmentMapper.toEntity(requestDTO);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment saveAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(saveAppointment);
    }
    @Transactional
    public AppointmentDTO reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(updatedAppointment);
    }
    @Transactional
    public AppointmentDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setAppointmentTime(requestDTO.getAppointmentTime());
        appointment.setReason(requestDTO.getReason());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(updatedAppointment);
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
