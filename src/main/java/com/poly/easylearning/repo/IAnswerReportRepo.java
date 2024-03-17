package com.poly.easylearning.repo;

import com.poly.easylearning.entity.AnswerReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAnswerReportRepo extends JpaRepository<AnswerReport, UUID> {
}
