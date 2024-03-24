package com.poly.easylearning.repo;

import com.poly.easylearning.entity.TestReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ITestReportRepo extends JpaRepository<TestReport, UUID> {
    @Query("SELECT t FROM TestReport t WHERE " +
            "((:userId IS NULL OR t.userInfo.id = :userId) " +
            "AND (((:dateStart IS NULL AND :dateEnd IS NULL) OR t.doingDate BETWEEN :dateStart AND :dateEnd) OR (:dateStart IS NULL AND t.doingDate <= :dateEnd) OR (:dateEnd IS NULL AND t.doingDate <= :dateStart)) " +
            "AND (:createdBy IS NULL OR t.createdBy = :createdBy)) " +
            "AND (t.isDeleted = false )")
    Page<TestReport> searchTestReport(Integer userId, LocalDateTime dateStart, LocalDateTime dateEnd, UUID createdBy, Pageable pageable);

    @Query("SELECT t FROM TestReport t WHERE " +
            "(t.id = :id)" + " AND (t.isDeleted = false )")
    Optional<TestReport> getTestReportById(UUID id);

    @Query("SELECT COUNT(t) as doingTestAmountQuarter " +
            "FROM TestReport t WHERE (date(t.createdDate) BETWEEN :dateStart AND :dateEnd) " +
            "AND t.isDeleted = false")
    Map<String, Long> getDoingTestAmount(LocalDate dateStart, LocalDate dateEnd);
}
