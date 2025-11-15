package com.example.demo.repository;

import com.example.demo.entity.Department;
import com.example.demo.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);
    List<Department> findByNameContainingIgnoreCase(String name);

    @Query("SELECT d FROM Department d WHERE d.headDoctor.id = :doctorId")
    Optional<Department> findByDoctorId(@Param("doctorId") Long DoctorId);

    @Query("SELECT d.name, SIZE(d.doctors) FROM Department d")
    List<Object[]> countDoctorsPerDepartment();

    @Query("SELECT d FROM Department d WHERE d.headDoctor IS NULL")
    List<Department> findDepartmentsWithoutHeadDoctor();

    @Query("SELECT doc FROM Doctor doc WHERE doc NOT IN(SELECT d FROM Department dept JOIN dept.doctors d)")
    List<Doctor> findDoctorsWithoutDepartment();
}