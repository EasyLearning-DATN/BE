package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Report;
import com.poly.easylearning.enums.ReportStatus;
import com.poly.easylearning.enums.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IReportRepo extends JpaRepository<Report, UUID> {

	@Query("""
			SELECT o FROM Report o
			WHERE o.type = :type
			AND o.status = :status
			AND o.isDeleted != TRUE
			""")
	Page<Report> findAllByCondition(ReportType type, ReportStatus status,Pageable pageable);


	@Query("""
			SELECT o FROM Report o
			WHERE o.id = :reportId
			AND o.isDeleted != TRUE
			""")
	Optional<Report> findById(UUID reportId);
}
