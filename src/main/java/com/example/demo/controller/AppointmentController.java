package com.example.demo.controller;

import com.example.demo.dto.AppointmentDTO;
import com.example.demo.dto.AppointmentRequestDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(){
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
    @GetMapping("/page")
    public ResponseEntity<Page<AppointmentDTO>> getAllAppointments(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size,@RequestParam(defaultValue = "appointmentTime")String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return ResponseEntity.ok(appointmentService.getAllAppointments(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id){
        return appointmentService.getAppointmentById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<AppointmentDTO> getAppointmentWithDetails(@PathVariable Long id){
        return appointmentService.getAppointmentWithDetails(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentByPatient(@PathVariable Long patientId){
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentByDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(appointmentService.getAppointmentByDoctor(doctorId));
    }
    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentDTO>> getUpcomingAppointments(){
        return ResponseEntity.ok(appointmentService.getUpcomingAppointments());
    }
    @GetMapping("/date-range")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentByDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime start, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime end){
        return ResponseEntity.ok(appointmentService.getAppointmentsByDateRange(start,end));
    }
    @GetMapping("/doctor/{doctorId}/date-range")
    public ResponseEntity<List<AppointmentDTO>> getDoctorAppointmentByDateRange(@PathVariable Long doctorId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime start,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime end){
        return ResponseEntity.ok(appointmentService.getDoctorAppointmentByDateRange(doctorId,start,end));
    }
    @GetMapping("/stats/doctor-count")
    public ResponseEntity<Map<String,Long>> getAppointmentCountByDoctor(){
        return ResponseEntity.ok(appointmentService.getAppointmentCountByDoctor());
    }
    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAppointmentAvailability(@RequestParam Long doctorId,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime appointmentTime){
        return ResponseEntity.ok(appointmentService.isAppointmentTimeAvailable(doctorId, appointmentTime));
    }
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentRequestDTO requestDTO, @RequestParam Long doctorId, @RequestParam Long patientId){
        return ResponseEntity.ok(appointmentService.createNewAppointment(requestDTO, doctorId, patientId));
    }
    @PutMapping
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequestDTO requestDTO){
        return ResponseEntity.ok(appointmentService.updateAppointment(id, requestDTO));
    }
    @PostMapping("/{appointmentId}/reassign/{doctorId}")
    public ResponseEntity<AppointmentDTO> reassignAppointment(@PathVariable Long appointmentId, @PathVariable Long doctorId){
        return ResponseEntity.ok((appointmentService.reAssignAppointmentToAnotherDoctor(appointmentId, doctorId)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }
}
