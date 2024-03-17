package com.poly.easylearning.service;

import com.poly.easylearning.entity.Question;
import com.poly.easylearning.entity.QuestionReport;
import com.poly.easylearning.entity.QuestionTest;
import com.poly.easylearning.entity.TestReport;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.ReportItemRequest;

import java.util.List;
import java.util.UUID;

public interface IQuestionReportService {
    List<QuestionReport> getListQuestionReport();

    QuestionReport getOneQuestionReport(UUID id) throws DataNotFoundException;

    void createListQuestionReport(List<QuestionReport> questionReports, TestReport testReport);
    void createListQuestionReportAndAnswerReport(List<ReportItemRequest> reportItemRequests, TestReport testReport);
}
