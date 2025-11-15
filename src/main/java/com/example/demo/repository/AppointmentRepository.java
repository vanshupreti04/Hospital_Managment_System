package com.example.demo.repository;

import com.example.demo.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentTime > :currentTime ORDER BY a.appointmentTime ASC")
    List<Appointment> findUpcomingAppointments(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentTime BETWEEN :start AND :end")
    List<Appointment> findByDoctorAndDateRange(@Param("doctorId") Long doctorId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT a.doctor, Count(a) FROM Appointment a GROUP BY a.doctor")
    List<Object[]> countAppointmentByDoctor();

    @Query("SELECT a FROM Appointment a JOIN FETCH a.patient JOIN FETCH a.doctor WHERE a.id = :id")
    Optional<Appointment> findByIdWithDetails(@Param("id") Long id);

    Page<Appointment> findAll(Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentTime = :appointmentTime")
    List<Appointment> findOverlappingAppointments(@Param("doctorId") Long doctorId, @Param("appointmentTime") LocalDateTime appointmentTime);

}