package com.poly.easylearning.service;

import com.poly.easylearning.entity.QuestionReport;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.QuestionRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;

import java.util.List;
import java.util.UUID;

public interface IQuestionReportService {
    List<QuestionReport> getListQuestionReport();

    QuestionReport getOneQuestionReport(UUID id) throws DataNotFoundException;

    List<QuestionReport> createListQuestionReport(List<QuestionReport> questionReports);
}
