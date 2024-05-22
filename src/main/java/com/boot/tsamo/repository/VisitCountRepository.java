package com.boot.tsamo.repository;

import com.boot.tsamo.entity.VisitCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitCountRepository extends JpaRepository<VisitCount, Long> {
    Optional<VisitCount> findByIpAddressAndVisitDate(String ipAddress, LocalDate visitDate);
    List<VisitCount> findAllByVisitDate(LocalDate visitDate);
}