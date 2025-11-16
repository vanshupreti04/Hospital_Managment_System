package com.example.demo.mapper;

import com.example.demo.dto.AppointmentDTO;
import com.example.demo.dto.AppointmentRequestDTO;
import com.example.demo.entity.Appointment;

public class AppointmentMapper {
    public static AppointmentDTO toDTO(Appointment appointment){
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setReason(appointment.getReason());
        dto.setPatient(PatientMapper.toLiteDTO(appointment.getPatient()));
        dto.setDoctor(DoctorMapper.toLiteDTO(appointment.getDoctor()));
        return dto;
    }
    public static Appointment toEntity(AppointmentRequestDTO dto){
        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setReason(dto.getReason());
        return appointment;
    }
}
