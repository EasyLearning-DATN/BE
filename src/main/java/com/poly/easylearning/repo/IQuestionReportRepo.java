package com.poly.easylearning.repo;

import com.poly.easylearning.entity.QuestionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IQuestionReportRepo extends JpaRepository<QuestionReport, UUID> {
}