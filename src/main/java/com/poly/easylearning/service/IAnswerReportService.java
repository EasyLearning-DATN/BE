package com.poly.easylearning.service;

import com.poly.easylearning.entity.AnswerReport;

import java.util.List;
import java.util.UUID;

public interface IAnswerReportService {
    List<AnswerReport> createAllAnswerReport(List<AnswerReport> answerReports, UUID questionReportId);
}
