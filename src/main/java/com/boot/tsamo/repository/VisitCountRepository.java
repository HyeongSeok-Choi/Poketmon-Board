package com.boot.tsamo.repository;

import com.boot.tsamo.entity.VisitCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitCountRepository extends JpaRepository<VisitCount, Long> {
    // 최근 7일 동안의 방문 날짜를 중복 없이 조회하는 메서드
    @Query("SELECT DISTINCT v.visitDate FROM VisitCount v WHERE v.visitDate >= :startDate ORDER BY v.visitDate ASC")
    List<LocalDate> findDistinctVisitDates(LocalDate startDate);

    Optional<VisitCount> findByIpAddressAndVisitDate(String ipAddress, LocalDate visitDate);

    List<VisitCount> findAllByVisitDate(LocalDate visitDate);

    // 날짜별 방문자 수를 집계하는 메서드
    @Query("SELECT v.visitDate, COUNT(v) FROM VisitCount v WHERE v.visitDate >= :startDate GROUP BY v.visitDate ORDER BY v.visitDate ASC")
    List<Object[]> countVisitsByDateSince(LocalDate startDate);
}