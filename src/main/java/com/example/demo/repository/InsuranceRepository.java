package com.example.demo.repository;

import com.example.demo.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    Optional<Insurance> findByPolicyNumber(String policyNumber);
    List<Insurance> findByProviderContainingIgnoreCase(String provider);

    @Query("SELECT i FROM Insurance i WHERE i.validUntil BETWEEN :startDate AND :endDate")
    List<Insurance> findExpiringPolicies(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT i FROM Insurance i WHERE i.validUntil < :currentDate")
    List<Insurance> findExpiredPolicies(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT i.provider, COUNNT(i) FROM Insurance i GROUP BY i.provider")
    List<Object[]> countPoliciesByProvider();

    @Query("SELECT i FROM Insurance i LEFT JOIN FETCH i.patient")
    List<Insurance> findAllWithPatient();
}