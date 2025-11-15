package com.example.demo.repository;

import com.example.demo.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecilizationContainingIgnoreCase(String speccilization);
    List<Doctor> findByNameContainingIgnoreCase(String name);
    Optional<Doctor> findByEmail(String email);

    @Query("SELECT d, COUNT(a) as appointmentCount FROM Doctor d LEFT JOIN d.appointments a GROUP BY d")
    List<Object[]> findDoctorsWithAppointmentCount();

    @Query("SELECT d FROM Doctor d WHERE SIZE(d.appointments) < 5")
    List<Doctor> findAvailableDoctors();

    @Query("SELECT d FROM Doctor d JOIN d.departments dept WHERE dept.id = :departmentId")
    List<Doctor> findByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT d FROM Doctor d WHERE d IN (SELECT dept.headDoctor FROM Department dept)")
    List<Doctor> findHeadDoctors();

    Page<Doctor> findAll(Pageable pageable);

    @Query("SELECT d.specilization, COUNT(d) FROM Doctor d GROUP BY d.specilization")
    List<Object[]> countDoctorsBySpecilization();
}