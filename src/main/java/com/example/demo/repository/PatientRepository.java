package com.example.demo.repository;

import com.example.demo.dto.BloodGroupCountResponseEntity;
import com.example.demo.entity.Patient;
import com.example.demo.entity.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    Patient findByName(String name);
    List<Patient> findByNameContaining(String query);

    @Query("SELECT p FROM Patient p where p.bloodgroup = ?1")
    List<Patient> findByBloodGroup(@Param("bloodgroup") BloodGroupType bloodgroup);

    @Query("select p from Patient p where p.birthdate > :birthdate")
    List<Patient> findByBornAfterDate(@Param("birthdate")LocalDate birthdate);

    @Query("select new com.example.demo.dto.BloodGroupCountResponseEntity(p.bloodgroup, Count(p)) from Patient p group by p.bloodgroup")
//    List<Object[]> countEachBloodGroupType();
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();

    @Query(value = "select * from patient", nativeQuery = true)
    Page<Patient> findAllPatient(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = :name where p.id = :id")
    int updateNameWithId(@Param("name") String name, @Param("id") int id);

    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments a LEFT JOIN FETCH a.doctor")
    List<Patient> findAllPatientWithAppointment();
}
